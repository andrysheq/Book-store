package com.example.library.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class BookMetrics {

    private final Counter bookCreationCounter;
    private final Counter bookCreationErrorCounter;

    public BookMetrics(MeterRegistry meterRegistry) {
        this.bookCreationCounter = Counter.builder("book.creation.total")
                .description("Общее количество созданных книг")
                .tag("operation", "create")
                .register(meterRegistry);

        this.bookCreationErrorCounter = Counter.builder("book.creation.errors")
                .description("Количество ошибок при создании книг")
                .tag("operation", "create")
                .register(meterRegistry);
    }

    public void incrementBookCreationCounter() {
        bookCreationCounter.increment();
    }

    public void incrementBookCreationErrorCounter() {
        bookCreationErrorCounter.increment();
    }
}