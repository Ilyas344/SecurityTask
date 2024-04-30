package t1academy.securitytask.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import t1academy.securitytask.dto.auth.JwtResponse;
import t1academy.securitytask.model.user.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JwtMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    JwtResponse toDto(User user);


}