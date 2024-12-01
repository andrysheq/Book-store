package com.andrysheq.authservice.kafka;

import com.andrysheq.authservice.security.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {
    private final UserService userService;
    private final UserProducer userProducer;

    public UserConsumer(UserService userService, UserProducer userProducer) {
        this.userService = userService;
        this.userProducer = userProducer;
    }

    @KafkaListener(topics = "object-add-request", groupId = "user-service-group")
    public void consumeAddRequest(String message) {
        String[] parts = message.split(":");
        Long objectId = Long.valueOf(parts[0]);
        Long userId = Long.valueOf(parts[1]);

        userService.incrementRegisteredObjects(userId);

        String confirmationTime = String.valueOf(System.currentTimeMillis());
        userProducer.sendAddResponse(objectId);
    }
}

