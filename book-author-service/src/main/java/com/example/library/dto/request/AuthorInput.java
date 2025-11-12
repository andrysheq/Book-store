package com.example.library.dto.request;

import com.example.library.dto.enums.Gender;

public record AuthorInput(
        String firstName,
        String lastName,
        String middleName,
        Gender gender,
        String birthDate
) {}
