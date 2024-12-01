package com.andrysheq.authservice.security.service;

import com.andrysheq.authservice.repos.UserRepository;
import com.andrysheq.authservice.security.Role;
import com.andrysheq.authservice.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

//    /**
//     * Сохранение пользователя
//     *
//     * @return сохраненный пользователь
//     */
    public User save(User user) {
        return repository.save(user);
    }


//    /**
//     * Создание пользователя
//     *
//     * @return созданный пользователь
//     */
    public User create(User user) {

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = repository.findByUsername(username).
//                orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
//    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    }

    public void incrementRegisteredObjects(Long userId) {
        User user = getById(userId);
        Integer registeredObjects = user.getRegisteredObjects();
        user.setRegisteredObjects(++registeredObjects);
        repository.save(user);
    }

//    /**
//     * Получение пользователя по имени пользователя
//     * <p>
//     * Нужен для Spring Security
//     *
//     * @return пользователь
//     */
//    public UserDetailsService userDetailsService() {
//        return this::getByUsername;
//    }

//    /**
//     * Получение текущего пользователя
//     *
//     * @return текущий пользователь
//     */
//    public User getCurrentUser() {
//        // Получение имени пользователя из контекста Spring Security
//        var username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return getByUsername(username);
//    }
//
//
//    /**
//     * Выдача прав администратора текущему пользователю
//     * <p>
//     * Нужен для демонстрации
//     */
//    @Deprecated
//    public void getAdmin() {
//        var user = getCurrentUser();
//        user.setRole(Role.ROLE_ADMIN);
//        save(user);
//    }
}

