package com.example.library.query;

//import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.example.library.entity.AuthorEntity;
//import com.example.library.service.repo.AuthorRepoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class AuthorQuery implements GraphQLQueryResolver {
//
//    private final AuthorRepoService authorRepoService;
//
//    public List<AuthorEntity> getAuthors() {
//        return this.authorRepoService.findAll();
//    }
//
//    public AuthorEntity getVehicle(Long id) {
//        return this.authorRepoService.findById(id);
//    }
//}