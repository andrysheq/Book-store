package com.example.library.aop;

import com.example.library.metrics.BookMetrics;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookMetricsAspect {

    private final BookMetrics bookMetrics;

    public BookMetricsAspect(BookMetrics bookMetrics) {
        this.bookMetrics = bookMetrics;
    }

    @Around("@annotation(TrackBookCreation)")
    public Object trackBookCreation(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            bookMetrics.incrementBookCreationCounter();
            return result;
        } catch (Exception e) {
            bookMetrics.incrementBookCreationErrorCounter();
            throw e;
        }
    }
}
