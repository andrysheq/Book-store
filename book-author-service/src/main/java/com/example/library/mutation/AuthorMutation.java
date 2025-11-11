package com.example.library.mutation;

//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
//import com.example.library.dto.enums.Gender;
//import com.example.library.dto.request.AuthorRecord;
//import com.example.library.entity.AuthorEntity;
//import com.example.library.service.repo.AuthorRepoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import java.time.LocalDate;
//
//@Component
//@RequiredArgsConstructor
//public class AuthorMutation implements GraphQLMutationResolver {
//
//    private final AuthorRepoService authorRepoService;
//
//    public AuthorEntity createAuthor(String firstName, String lastName, String middleName, LocalDate birthDate, Gender gender) {
//        AuthorRecord authorRecord = new AuthorRecord();
//        authorRecord.setBirthDate(birthDate);
//        authorRecord.setGender(gender);
//        authorRecord.setFirstName(firstName);
//        authorRecord.setLastName(lastName);
//        authorRecord.setMiddleName(middleName);
//        return this.authorRepoService.saveAuthor(authorRecord);
//    }
//}