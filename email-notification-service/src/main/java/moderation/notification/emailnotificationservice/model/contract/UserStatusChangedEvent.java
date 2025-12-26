package moderation.notification.emailnotificationservice.model.contract;

public record UserStatusChangedEvent(
        String email,
        String firstName,
        Integer newStatusId
) {}

