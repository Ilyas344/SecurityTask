package t1academy.securitytask.service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import t1academy.securitytask.config.TestConfig;
import t1academy.securitytask.dto.auth.JwtRequest;
import t1academy.securitytask.dto.auth.JwtResponse;
import t1academy.securitytask.mappers.JwtMapper;
import t1academy.securitytask.model.exception.ResourceNotFoundException;
import t1academy.securitytask.model.user.Role;
import t1academy.securitytask.model.user.User;
import t1academy.securitytask.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private JwtMapper jwtMapper;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthServiceImpl authService;

    @Test
    void login() {
        Long userId = 1L;
        String email = "1234@mail.com";
        String username = "username";
        String password = "password";
        Set<Role> roles = Collections.emptySet();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest request = new JwtRequest();
        request.setEmail(email);
        request.setPassword(password);
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setRoles(roles);
        when(userService.getByEmail(email))
                .thenReturn(user);
        when(jwtMapper.toDto(user))
                .thenReturn(new JwtResponse(userId, email, null, null));
        when(tokenProvider.createAccessToken(userId, username, roles))
                .thenReturn(accessToken);
        when(tokenProvider.createRefreshToken(userId, username))
                .thenReturn(refreshToken);

        JwtResponse response = authService.login(request);
        System.out.println(response);
        Mockito.verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        Assertions.assertEquals(response.getEmail(), email);
        Assertions.assertEquals(response.getId(), userId);
        Assertions.assertNotNull(response.getAccessToken());
        Assertions.assertNotNull(response.getRefreshToken());
    }

    @Test
    void login_Success() throws Exception {
        Long userId = 1L;
        String username = "username";
        String email = "username@example.com"; // Используйте email
        String password = "password";
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        JwtRequest request = new JwtRequest(email, password);

        User user = new User(userId, username, email, null, Collections.emptySet());

        when(userService.getByEmail(email)).thenReturn(user);
        when(tokenProvider.createAccessToken(userId, email, user.getRoles()))
                .thenReturn(accessToken);
        when(tokenProvider.createRefreshToken(userId, email))
                .thenReturn(refreshToken);
        when(jwtMapper.toDto(user)).thenReturn(new JwtResponse(userId, email, null, null));

        JwtResponse response = authService.login(request);

        Mockito.verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Assertions.assertEquals(response.getEmail(), email);
        Assertions.assertEquals(response.getId(), userId);
        Assertions.assertEquals(response.getAccessToken(), accessToken);
        Assertions.assertEquals(response.getRefreshToken(), refreshToken);
    }


    @Test
    void loginWithIncorrectUsername() {
        String username = "username";
        String password = "password";
        JwtRequest request = new JwtRequest();
        request.setEmail(username);
        request.setPassword(password);
        User user = new User();
        user.setUsername(username);
        when(userService.getByUsername(username))
                .thenThrow(ResourceNotFoundException.class);
        Mockito.verifyNoInteractions(tokenProvider);
        Assertions.assertThrows(NullPointerException.class, () -> authService.login(request));
    }

    @Test
    void refresh() {
        String refreshToken = "refreshToken";
        String accessToken = "accessToken";
        String newRefreshToken = "newRefreshToken";
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(newRefreshToken);
        when(tokenProvider.refreshUserTokens(refreshToken))
                .thenReturn(response);
        JwtResponse testResponse = authService.refresh(refreshToken);
        Mockito.verify(tokenProvider).refreshUserTokens(refreshToken);
        Assertions.assertEquals(testResponse, response);
    }

}
