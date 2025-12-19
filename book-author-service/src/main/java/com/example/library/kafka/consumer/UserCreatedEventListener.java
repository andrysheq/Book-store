package com.example.library.kafka.consumer;

import com.example.library.model.dao.UserEntity;
import com.example.library.model.enums.UserStatusEnum;
import com.example.library.model.event.UserCreatedEvent;
import com.example.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventListener {

    private final UserRepository userRepository;

    @KafkaListener(
            topics = "user.created",
            groupId = "book-author-group",
            containerFactory = "userEventKafkaListenerContainerFactory"
    )
    @Transactional
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Получено событие создания пользователя: {}", event.getEmail());

        // Проверка на дубликат
        if (userRepository.existsByEmail(event.getEmail())) {
            log.warn("Пользователь {} уже существует, пропускаем", event.getEmail());
            return;
        }

        // Создать UserEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(event.getUserId());
        userEntity.setEmail(event.getEmail());
        userEntity.setFirstName(event.getFirstName());
        userEntity.setUserStatus(event.getIsActive() ? UserStatusEnum.ACTIVE : UserStatusEnum.BLOCKED);

        // Сохранить
        userRepository.save(userEntity);
        log.info("Пользователь {} синхронизирован в book-author-service", event.getEmail());
    }
}
