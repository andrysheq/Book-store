package moderation.user.usermoderationservice.service;

import lombok.RequiredArgsConstructor;
import moderation.user.usermoderationservice.exception.NotFoundException;
import moderation.user.usermoderationservice.kafka.UserEventProducer;
import moderation.user.usermoderationservice.mapper.user.UserStatusChangedEventConverter;
import moderation.user.usermoderationservice.model.contract.UserRegistryRequest;
import moderation.user.usermoderationservice.model.contract.UserStatusChangedEvent;
import moderation.user.usermoderationservice.model.dao.UserEntity;
import moderation.user.usermoderationservice.model.enums.UserStatusEnum;
import moderation.user.usermoderationservice.repository.UserRepository;
import moderation.user.usermoderationservice.service.spec.UserSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;
    private final UserStatusChangedEventConverter userStatusChangedEventConverter;

    public UserEntity setUserStatus(Long userId, Integer statusId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Пользователь с id = " + userId + " не найден"));
        if(userEntity.getStatus().getId().equals(statusId)){
            return userEntity;
        }
        userEntity.setStatus(UserStatusEnum.of(statusId));
        userEntity.setStatusUpdatedAt(LocalDateTime.now());

        UserEntity savedEntity = userRepository.save(userEntity);
        UserStatusChangedEvent event = userStatusChangedEventConverter.toUserStatusChangedEvent(savedEntity);
        userEventProducer.sendUserStatusChanged(event);
        return savedEntity;
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
