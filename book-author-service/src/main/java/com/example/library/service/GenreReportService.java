package com.example.library.service;

import com.example.library.dto.GenreReportDto;
import com.example.library.entity.BookEntity;
import com.example.library.entity.GenreEntity;
import com.example.library.entity.ReviewEntity;
import com.example.library.entity.ReviewStatusEntity;
import com.example.library.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GenreReportService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewStatusRepository reviewStatusRepository;

    /**
     * Получить полный отчет по жанрам с рейтингом и отзывами
     * Все операции группировки в Java
     */
    public List<GenreReportDto> getGenreReport() {
        log.info("Запрос отчета по жанрам с рейтингом");

        // Получить все сущности
        List<GenreEntity> allGenres = genreRepository.findAll();
        List<BookEntity> allBooks = bookRepository.findAll();
        List<ReviewEntity> allReviews = reviewRepository.findAll();
        ReviewStatusEntity approvedStatus = reviewStatusRepository.getById(2L);

        // Фильтруем только одобренные отзывы
        List<ReviewEntity> approvedReviews = allReviews.stream()
                .filter(r -> r.getReviewStatus() != null &&
                        r.getReviewStatus().getId().equals(approvedStatus.getId()))
                .toList();

        // Группируем отзывы по книгам
        Map<Integer, List<ReviewEntity>> reviewsByBookId = approvedReviews.stream()
                .collect(Collectors.groupingBy(r -> r.getBook().getId()));

        // Группируем книги по жанрам
        Map<Integer, List<BookEntity>> booksByGenreId = allBooks.stream()
                .collect(Collectors.groupingBy(b -> b.getGenre().getId()));

        // Строим отчет по каждому жанру
        return allGenres.stream()
                .map(genre -> {
                    List<BookEntity> genreBooks = booksByGenreId.getOrDefault(genre.getId(), List.of());

                    // Получаем все отзывы для книг этого жанра
                    List<ReviewEntity> genreReviews = genreBooks.stream()
                            .flatMap(book -> reviewsByBookId.getOrDefault(book.getId(), List.of()).stream())
                            .toList();

                    // Вычисляем статистику
                    long bookCount = genreBooks.size();
                    long reviewCount = genreReviews.size();

                    BigDecimal averageRating = null;
                    Integer minRating = null;
                    Integer maxRating = null;

                    if (!genreReviews.isEmpty()) {
                        // Средний рейтинг
                        averageRating = genreReviews.stream()
                                .map(r -> BigDecimal.valueOf(r.getRating()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(BigDecimal.valueOf(genreReviews.size()), 2, RoundingMode.HALF_UP);

                        // Минимальный рейтинг
                        minRating = genreReviews.stream()
                                .mapToInt(ReviewEntity::getRating)
                                .min()
                                .orElse(999);

                        // Максимальный рейтинг
                        maxRating = genreReviews.stream()
                                .mapToInt(ReviewEntity::getRating)
                                .max()
                                .orElse(999);
                    }

                    return GenreReportDto.builder()
                            .genreName(genre.getName())
                            .bookCount(bookCount)
                            .averageRating(averageRating)
                            .reviewCount(reviewCount)
                            .minRating(minRating)
                            .maxRating(maxRating)
                            .build();
                })
                .sorted(Comparator
                        .comparingLong(GenreReportDto::getReviewCount).reversed()
                        .thenComparing(GenreReportDto::getGenreName))
                .toList();
    }

    /**
     * Получить отчет для конкретного жанра
     */
    public GenreReportDto getGenreReportByName(String genreName) {
        log.info("Запрос отчета для жанра: {}", genreName);
        return getGenreReport()
                .stream()
                .filter(g -> g.getGenreName().equalsIgnoreCase(genreName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Жанр не найден: " + genreName));
    }

    /**
     * Получить топ жанров по количеству отзывов
     */
    public List<GenreReportDto> getTopGenresByReviewCount(int limit) {
        log.info("Получение топ {} жанров по отзывам", limit);
        return getGenreReport()
                .stream()
                .limit(limit)
                .toList();
    }

    /**
     * Получить жанры с рейтингом выше указанного
     */
    public List<GenreReportDto> getGenresByMinAverageRating(BigDecimal minRating) {
        log.info("Получение жанров с рейтингом >= {}", minRating);
        return getGenreReport()
                .stream()
                .filter(g -> g.getAverageRating() != null &&
                        g.getAverageRating().compareTo(minRating) >= 0)
                .toList();
    }

    /**
     * Получить жанры с минимальным количеством книг
     */
    public List<GenreReportDto> getGenresByMinBookCount(long minCount) {
        log.info("Получение жанров с минимум {} книг", minCount);
        return getGenreReport()
                .stream()
                .filter(g -> g.getBookCount() >= minCount)
                .toList();
    }

    /**
     * Получить жанры с минимальным количеством отзывов
     */
    public List<GenreReportDto> getGenresByMinReviewCount(long minCount) {
        log.info("Получение жанров с минимум {} отзывов", minCount);
        return getGenreReport()
                .stream()
                .filter(g -> g.getReviewCount() >= minCount)
                .toList();
    }

    /**
     * Получить жанры отсортированные по среднему рейтингу (сначала выше)
     */
    public List<GenreReportDto> getGenresSortedByRating() {
        log.info("Получение жанров отсортированных по рейтингу");
        return getGenreReport()
                .stream()
                .sorted(Comparator.comparing(GenreReportDto::getAverageRating,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    /**
     * Получить жанры отсортированные по количеству книг (сначала больше)
     */
    public List<GenreReportDto> getGenresSortedByBookCount() {
        log.info("Получение жанров отсортированных по количеству книг");
        return getGenreReport()
                .stream()
                .sorted(Comparator.comparingLong(GenreReportDto::getBookCount).reversed())
                .toList();
    }

    /**
     * Получить статистику по всем жанрам
     */
    public Map<String, Object> getGenresStatistics() {
        log.info("Получение общей статистики по жанрам");
        List<GenreReportDto> reports = getGenreReport();

        long totalGenres = reports.size();
        long totalBooks = reports.stream().mapToLong(GenreReportDto::getBookCount).sum();
        long totalReviews = reports.stream().mapToLong(GenreReportDto::getReviewCount).sum();

        BigDecimal overallAverageRating = reports.stream()
                .filter(g -> g.getAverageRating() != null)
                .map(GenreReportDto::getAverageRating)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(Math.max(1,
                                reports.stream().filter(g -> g.getAverageRating() != null).count())),
                        2, RoundingMode.HALF_UP);

        GenreReportDto topGenre = reports.stream().findFirst().orElse(null);

        return Map.of(
                "totalGenres", totalGenres,
                "totalBooks", totalBooks,
                "totalReviews", totalReviews,
                "overallAverageRating", overallAverageRating,
                "topGenre", topGenre
        );
    }

    /**
     * Получить жанры в диапазоне рейтинга
     */
    public List<GenreReportDto> getGenresByRatingRange(BigDecimal minRating, BigDecimal maxRating) {
        log.info("Получение жанров с рейтингом в диапазоне {} - {}", minRating, maxRating);
        return getGenreReport()
                .stream()
                .filter(g -> g.getAverageRating() != null &&
                        g.getAverageRating().compareTo(minRating) >= 0 &&
                        g.getAverageRating().compareTo(maxRating) <= 0)
                .toList();
    }
}

