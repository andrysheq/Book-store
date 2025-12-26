package moderation.notification.emailnotificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moderation.notification.emailnotificationservice.model.contract.UserStatusChangedEvent;
import moderation.notification.emailnotificationservice.service.EmailNotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserStatusChangedListener {

    private final EmailNotificationService emailService;

    @KafkaListener(
            topics = "${app.kafka.topics.user-status-changed}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleUserStatusChanged(UserStatusChangedEvent event) {
        log.info("Получено событие смены статуса пользователя: email={}, newStatusId={}",
                event.email(), event.newStatusId());

        try {
            emailService.sendStatusChangedEmail(event);
        } catch (Exception ex) {
            log.error("Ошибка при отправке email пользователю ({})", event.email(), ex);
        }
    }
}

