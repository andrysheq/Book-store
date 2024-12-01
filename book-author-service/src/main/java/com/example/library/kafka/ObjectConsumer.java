package com.example.library.kafka;

import com.example.library.service.BookService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ObjectConsumer {
    private final BookService bookService;

    public ObjectConsumer(BookService bookService) {
        this.bookService = bookService;
    }

    @KafkaListener(topics = "object-add-response", groupId = "object-service-group")
    public void consumeAddResponse(String message) {
        Long objectId = Long.valueOf(message);

        bookService.confirmObjectAddition(objectId);
    }
}

