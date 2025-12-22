package moderation.user.usermoderationservice.handler;

import lombok.RequiredArgsConstructor;
import moderation.user.usermoderationservice.mapper.user.UserConverter;
import moderation.user.usermoderationservice.model.contract.UserRegistryRequest;
import moderation.user.usermoderationservice.model.contract.UserView;
import moderation.user.usermoderationservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserView getUserById(Long userId) {
        return userConverter.toUserView(userService.getUserById(userId));
    }

    public UserView setUserStatus(Long userId, Integer statusId) {
        return userConverter.toUserView(userService.setUserStatus(userId, statusId));
    }

    public Page<UserView> getUsers(UserRegistryRequest request, Pageable pageable) {
        return userService.getUserRegistry(request, pageable).map(userConverter::toUserView);
    }
}
