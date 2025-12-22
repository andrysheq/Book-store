package moderation.user.usermoderationservice.mapper.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import moderation.user.usermoderationservice.model.enums.UserStatusEnum;

/**
 * Converter для UserStatusEnum в базе данных
 */
@Converter(autoApply = true)
public class UserStatusEnumAttributeConverter implements AttributeConverter<UserStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserStatusEnum attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public UserStatusEnum convertToEntityAttribute(Integer dbData) {
        return UserStatusEnum.of(dbData);
    }
}
