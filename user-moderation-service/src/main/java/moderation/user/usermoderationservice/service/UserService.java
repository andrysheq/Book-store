package moderation.user.usermoderationservice.service;

import lombok.RequiredArgsConstructor;
import moderation.user.usermoderationservice.exception.NotFoundException;
import moderation.user.usermoderationservice.model.contract.UserRegistryRequest;
import moderation.user.usermoderationservice.model.dao.UserEntity;
import moderation.user.usermoderationservice.model.enums.UserStatusEnum;
import moderation.user.usermoderationservice.repository.UserRepository;
import moderation.user.usermoderationservice.service.spec.UserSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity setUserStatus(Long userId, Integer statusId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id = " + userId + " не найден"));
        userEntity.setStatus(UserStatusEnum.of(statusId));

        return userRepository.save(userEntity);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<UserEntity> getUserRegistry(UserRegistryRequest userRegistryRequest, Pageable pageable) {
        Specification<UserEntity> spec = buildSpecification(userRegistryRequest);

        return userRepository.findAll(spec, pageable);
    }

    private Specification<UserEntity> buildSpecification(UserRegistryRequest request) {
        return new UserSpecificationBuilder()
                .withStatus(UserStatusEnum.of(request.statusId()))
                .withSearchLike(request.searchLike())
                .build();
    }
}
