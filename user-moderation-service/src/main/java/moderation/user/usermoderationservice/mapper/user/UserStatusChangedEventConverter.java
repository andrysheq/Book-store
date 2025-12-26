package moderation.user.usermoderationservice.mapper.user;

import lombok.RequiredArgsConstructor;
import moderation.user.usermoderationservice.model.contract.UserStatusChangedEvent;
import moderation.user.usermoderationservice.model.dao.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStatusChangedEventConverter {

    public UserStatusChangedEvent toUserStatusChangedEvent(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserStatusChangedEvent(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getStatus().getId()
        );
    }
}
