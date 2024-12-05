package org.example.entites;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "accounts")
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "auth_id")
    private String authId;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    @Column(name = "is_locked")
    private Boolean isLocked=false;

    @Column(name = "is_deleted")
    private Boolean isDeleted=false;
}
