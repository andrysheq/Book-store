package com.example.library.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ObjectProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ObjectProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAddRequest(Long objectId, Long userId) {
        String message = objectId + ":" + userId;
        kafkaTemplate.send("object-add-request", message);
    }
}

