package t1academy.securitytask.web.mappers;

import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import t1academy.securitytask.model.user.User;
import t1academy.securitytask.web.dto.user.UserDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", source = "password", qualifiedByName = "passwordMapper")
    User partialUpdate(User oldUser, @MappingTarget UserDto user, @Context PasswordEncoder passwordEncoder);


    @Named("passwordMapper")
    default String passwordMapper(String password, @Context PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password", qualifiedByName = "passwordMapper")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    User userDtoMapper(UserDto user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    UserDto userMapper(User user);

}
