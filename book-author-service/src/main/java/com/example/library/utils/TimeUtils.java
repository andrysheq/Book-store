package com.example.library.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class TimeUtils {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_DATE;

    public static LocalDate parseIsoDate(String value) {
        try {
            return LocalDate.parse(value, ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid birthDate, expected ISO-8601 'yyyy-MM-dd'");
        }
    }
}
