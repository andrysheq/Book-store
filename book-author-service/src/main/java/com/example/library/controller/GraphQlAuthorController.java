package com.example.library.controller;

import com.example.library.dto.enums.Gender;
import com.example.library.dto.request.AuthorRecord;
import com.example.library.entity.AuthorEntity;
import com.example.library.service.repo.AuthorRepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

//@Controller
//@RequiredArgsConstructor
//public class GraphQlAuthorController {
//
//    private final AuthorRepoService authorRepoService;
//
//    @MutationMapping
//    public AuthorEntity createAuthor(@Argument String firstName,
//                                     @Argument String lastName,
//                                     @Argument String middleName,
//                                     @Argument LocalDate birthDate,
//                                     @Argument Gender gender) {
//        AuthorRecord authorRecord = new AuthorRecord();
//        authorRecord.setFirstName(firstName);
//        authorRecord.setLastName(lastName);
//        authorRecord.setMiddleName(middleName);
//        authorRecord.setBirthDate(birthDate);
//        authorRecord.setGender(gender);
//        return authorRepoService.saveAuthor(authorRecord);
//    }
//}
