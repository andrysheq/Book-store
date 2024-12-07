package com.example.authenticationservice.dto;

import jakarta.persistence.Column;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;
}
