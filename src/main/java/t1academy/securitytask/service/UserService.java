package t1academy.securitytask.service;

import t1academy.securitytask.model.user.User;
import t1academy.securitytask.dto.user.UserDto;

public interface UserService {

    UserDto getById(Long id);
    User getUserById(Long id);

    User getByUsername(String username);
    User getByEmail(String username);

    UserDto update(UserDto user);

    UserDto create(UserDto user);

    void delete(Long id);

}
