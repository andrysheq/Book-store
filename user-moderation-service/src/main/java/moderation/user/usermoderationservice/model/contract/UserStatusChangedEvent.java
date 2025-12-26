package moderation.user.usermoderationservice.model.contract;

public record UserStatusChangedEvent(
        String email,
        String firstName,
        Integer newStatusId
) {}

