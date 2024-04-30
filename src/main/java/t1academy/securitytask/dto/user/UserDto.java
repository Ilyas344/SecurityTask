package t1academy.securitytask.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User DTO")
public class UserDto {

    @Schema(
            description = "User id",
            example = "1"
    )
    @NotNull(
            message = "Id must be not null."
    )
    private Long id;

    @Schema(
            description = "User name",
            example = "John Doe"
    )
    @NotNull(
            message = "Name must be not null."
    )
    @Length(
            max = 255,
            message = "Name length must be smaller than 255 symbols."
    )
    private String username;

    @Schema(
            description = "User email",
            example = "johndoe@gmail.com"
    )
    @NotNull(
            message = "Username must be not null."
    )
    @Length(
            max = 255,
            message = "Username length must be smaller than 255 symbols."
    )
    private String email;

    @Schema(
            description = "User encrypted password"
    )
    @JsonProperty(
            access = JsonProperty.Access.WRITE_ONLY
    )
    @NotNull(
            message = "Password must be not null."
    )
    private String password;


}
