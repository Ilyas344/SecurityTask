package t1academy.securitytask.service.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import t1academy.securitytask.config.TestConfig;
import t1academy.securitytask.dto.user.UserDto;
import t1academy.securitytask.mappers.UserMapper;
import t1academy.securitytask.model.exception.ResourceNotFoundException;
import t1academy.securitytask.model.user.Role;
import t1academy.securitytask.model.user.User;
import t1academy.securitytask.repository.UserRepository;
import t1academy.securitytask.service.UserService;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Test
    public void testGetById_Success() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        User user = new User(id, username, email, "password", Set.of(Role.ROLE_USER));
        UserDto expectedUserDto = new UserDto(id, username, email, "password");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.userMapper(user)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.getById(id);

        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(id));
        verify(userRepository).findById(id);
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        User user = new User(id, username, email, "password", Set.of(Role.ROLE_USER));

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserById(id);

        assertNotNull(actualUser);
        assertEquals(user, actualUser);
    }

    @Test
    void getByUsername() {
        String username = "username@gmail.com";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        User testUser = userService.getByUsername(username);
        verify(userRepository).findByUsername(username);
        assertEquals(user, testUser);
    }

    @Test
    void getByNotExistingUsername() {
        String username = "username@gmail.com";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUsername(username));
        verify(userRepository).findByUsername(username);
    }


    @Test
    public void testUpdate() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";

        UserDto newUserDto = new UserDto(id, username, email, newPassword);
        User existingUser = new User(id, username, email, "oldPassword", Set.of(Role.ROLE_USER));
        UserDto expectedUserDto = new UserDto(id, username, email, null);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userMapper.updateUserFromDto(newUserDto, existingUser)).thenReturn(existingUser);
        when(userMapper.userMapper(existingUser)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.update(newUserDto);

        assertNotNull(actualUserDto);
        assertEquals(expectedUserDto, actualUserDto);
        verify(userRepository).save(existingUser);
    }

    @Test

    public void testUpdate_NotFound() throws Exception {
        Long id = 1L;
        String username = "user";
        String email = "user@example.com";
        String newPassword = "newPassword";

        UserDto newUserDto = new UserDto(id, username, email, newPassword);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(newUserDto));
    }


    @Test
    public void testCreate_Success() throws Exception {
        String username = "user";
        String email = "user@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        UserDto userDto = new UserDto(1L, username, email, password);
        User expectedUser = new User(1L, username, email, encodedPassword, Set.of(Role.ROLE_USER));

        when(userMapper.userDtoMapper(userDto)).thenReturn(expectedUser);
        when(userRepository.existsByUser(username, email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        when(userMapper.userMapper(expectedUser)).thenReturn(userDto); // Ожидается получение UserDto

        UserDto actualUserDto = userService.create(userDto);

        assertNotNull(actualUserDto);
        assertEquals(userDto, actualUserDto);
        verify(userRepository).save(expectedUser);
    }


    @Test
    void createWithExistingUsername() {
        String username = "username@gmail.com";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        var userDto = UserDto.builder()
                .id(1L)
                .username("username")
                .password("password")
                .build();
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new User()));
        when(passwordEncoder.encode(password))
                .thenReturn("encodedPassword");
        assertThrows(NullPointerException.class,
                () -> userService.create(userDto));
        verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void createWithDifferentPasswords() {
        var userDto = UserDto.builder()
                .id(1L)
                .username("username")
                .password("password")
                .build();
        String username = "username@gmail.com";
        String password = "password1";
        String passwordConfirmation = "password2";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        assertThrows(NullPointerException.class,
                () -> userService.create(userDto));
        verify(userRepository, Mockito.never()).save(user);
    }


    @Test
    void delete() {
        Long id = 1L;
        userService.delete(id);
        verify(userRepository).deleteById(id);
    }

}
