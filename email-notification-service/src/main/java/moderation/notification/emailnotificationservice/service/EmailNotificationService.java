package moderation.notification.emailnotificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moderation.notification.emailnotificationservice.model.contract.UserStatusChangedEvent;
import moderation.notification.emailnotificationservice.model.enums.UserStatusEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendStatusChangedEmail(UserStatusChangedEvent event) {
        String subject = buildSubject(event);
        String body = buildBody(event);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(event.email());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    private String buildSubject(UserStatusChangedEvent event) {
        return switch (event.newStatusId()) {
            case 1 -> "Ваш аккаунт разблокирован";
            case 2 -> "Ваш аккаунт заблокирован";
            default -> "Статус вашего аккаунта изменён";
        };
    }

    private String buildBody(UserStatusChangedEvent event) {
        String statusText = switch (event.newStatusId()) {
            case 1 -> "разблокирован";
            case 2 -> "заблокирован";
            default -> "изменён";
        };

        return """
                Здравствуйте, %s!

                Ваш аккаунт был %s.
       
                С уважением,
                Команда поддержки BookStore
                """.formatted(event.firstName(), statusText);
    }
}

