package t1academy.securitytask.mappers;

import org.mapstruct.*;
import t1academy.securitytask.dto.user.UserDto;
import t1academy.securitytask.model.user.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", ignore = true)
    User updateUserFromDto(UserDto userDto, @MappingTarget User existingUser);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User userDtoMapper(UserDto user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    UserDto userMapper(User user);

}
