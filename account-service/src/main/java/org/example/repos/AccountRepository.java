package org.example.repos;

import org.example.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsernameOrEmailAndIdNot(String username, String email, Long id);

    Optional<Account> findAccountByUsername(String username);
}
