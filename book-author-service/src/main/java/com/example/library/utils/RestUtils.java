package com.example.library.utils;

import com.example.library.dto.request.Request;
import lombok.experimental.UtilityClass;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.valueOf;

@UtilityClass
public class RestUtils {

    private static int getHttpStatus(Object result) {
        return HttpStatus.OK.value();
    }

    public static <T, U> ResponseEntity<U> responseOf(Request<T> request, Function<Request<T>, U> mapper) {
        U result = mapper.apply(request);

        return ResponseEntity.status(getHttpStatus(result))
                .body(result);
    }

    public static <Long, U> ResponseEntity<U> responseOf(Long entityId, Function<Long, U> mapper) {
        U result = mapper.apply(entityId);

        return ResponseEntity.status(getHttpStatus(result))
                .cacheControl(CacheControl.noCache())
                .cacheControl(CacheControl.noStore())
                .body(result);
    }

    public static <T> ResponseEntity<T> responseOf(Supplier<T> supplier) {
        T result = supplier.get();

        return ResponseEntity.status(getHttpStatus(result))
                .body(result);
    }

    public static <T> ResponseEntity<T> responseNoCacheOf(Supplier<T> supplier) {
        T result = supplier.get();

        return ResponseEntity.status(getHttpStatus(result))
                .cacheControl(CacheControl.noCache())
                .cacheControl(CacheControl.noStore())
                .body(result);
    }

    public static boolean is2xxSuccessful(int httpStatus) {
        return valueOf(httpStatus).is2xxSuccessful();
    }

}
