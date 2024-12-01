package com.andrysheq.authservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAddResponse(Long objectId) {
        String message = String.valueOf(objectId);
        kafkaTemplate.send("object-add-response", message);
    }
}
