package t1academy.securitytask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1academy.securitytask.model.exception.ResourceNotFoundException;
import t1academy.securitytask.model.exception.UserExistsException;
import t1academy.securitytask.model.user.Role;
import t1academy.securitytask.model.user.User;
import t1academy.securitytask.repository.UserRepository;
import t1academy.securitytask.service.UserService;
import t1academy.securitytask.dto.user.UserDto;
import t1academy.securitytask.mappers.UserMapper;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMap;


    @Override
    public UserDto getById(final Long id) {
        User user =userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        return userMap.userMapper(user);
    }
    @Override
    public User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    public User getByUsername(final String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }
    @Override
    public User getByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    public UserDto update(final UserDto newUser) {
        User oldUser = userRepository.findById(newUser.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(
                userMap.updateUserFromDto(newUser,oldUser));
        return userMap.userMapper(oldUser);

    }


    @Override
    public UserDto create(final UserDto userDto) {
        User user = userMap.userDtoMapper(userDto);

        if(userRepository.existsByUser(user.getUsername(),user.getEmail())){
            throw new UserExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        return userMap.userMapper(userRepository.save(user));
    }


    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

}
