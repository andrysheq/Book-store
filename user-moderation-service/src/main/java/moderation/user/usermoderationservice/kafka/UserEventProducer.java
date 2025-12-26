package moderation.user.usermoderationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moderation.user.usermoderationservice.model.contract.UserStatusChangedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventProducer {

    private final KafkaTemplate<String, UserStatusChangedEvent> kafkaTemplate;

    @Value("${app.kafka.topics.user-status-changed}")
    private String topic;


    public void sendUserStatusChanged(UserStatusChangedEvent event) {
        kafkaTemplate.send(topic, event.email(), event);
    }
}