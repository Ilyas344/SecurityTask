package t1academy.securitytask.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import t1academy.securitytask.api.UserApi;
import t1academy.securitytask.service.UserService;
import t1academy.securitytask.dto.user.UserDto;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;



    public ResponseEntity<UserDto> update(final UserDto dto) {
        log.info("UserController.update");
        return ResponseEntity.ok( userService.update(dto));
    }


    public ResponseEntity<UserDto> getById(final Long id) {
        log.info("UserController.getById");
        return ResponseEntity.ok(userService.getById(id));
    }


    public ResponseEntity<Void> deleteById(final Long id) {
        log.info("UserController.deleteById");
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
