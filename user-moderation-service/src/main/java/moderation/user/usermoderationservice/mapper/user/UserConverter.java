package moderation.user.usermoderationservice.mapper.user;

import lombok.RequiredArgsConstructor;
import moderation.user.usermoderationservice.model.dao.UserEntity;
import moderation.user.usermoderationservice.model.contract.UserView;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserView toUserView(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserView(
                entity.getId(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getStatus().getTitle(),
                entity.getAudit().getCreatedAt(),
                entity.getStatusUpdatedAt()
        );
    }
}
