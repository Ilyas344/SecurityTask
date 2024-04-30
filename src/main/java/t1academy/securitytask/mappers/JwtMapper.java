package t1academy.securitytask.web.mappers;

import org.mapstruct.*;
import t1academy.securitytask.model.user.User;
import t1academy.securitytask.web.dto.auth.JwtResponse;
import t1academy.securitytask.web.security.JwtTokenProvider;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface JwtMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "accessToken", qualifiedByName = "accessMapper")
    @Mapping(target = "refreshToken", qualifiedByName = "refreshMapper")
    JwtResponse toDto(User user);

    @Named("accessMapper")
    default String accessMapper(User user, @Context JwtTokenProvider jwtTokenProvider) {
        return jwtTokenProvider.createAccessToken(
                user.getId(), user.getUsername(), user.getRoles());
    }

    @Named("refreshMapper")
    default String refreshMapper(User user, @Context JwtTokenProvider jwtTokenProvider) {
        return jwtTokenProvider.createRefreshToken(
                user.getId(), user.getUsername());
    }

}