package t1academy.securitytask.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import t1academy.securitytask.api.AuthApi;
import t1academy.securitytask.dto.auth.JwtRequest;
import t1academy.securitytask.dto.auth.JwtResponse;
import t1academy.securitytask.dto.user.UserDto;
import t1academy.securitytask.service.AuthService;
import t1academy.securitytask.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final UserService userService;


    public ResponseEntity<JwtResponse> login(final JwtRequest loginRequest) {
        log.info("AuthController.login");
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    public ResponseEntity<UserDto> register(final UserDto userDto) {
        log.info("AuthController.register");
        return ResponseEntity.ok(userService.create(userDto));
    }

    public ResponseEntity<JwtResponse> refresh(final String refreshToken) {
        log.info("AuthController.refresh");
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

}
