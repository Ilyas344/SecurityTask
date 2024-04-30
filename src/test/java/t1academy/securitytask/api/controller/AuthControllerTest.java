package t1academy.securitytask.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import t1academy.securitytask.config.ApplicationConfig;
import t1academy.securitytask.dto.auth.JwtRequest;
import t1academy.securitytask.dto.auth.JwtResponse;
import t1academy.securitytask.dto.user.UserDto;
import t1academy.securitytask.service.AuthService;
import t1academy.securitytask.service.impl.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
@Import(ApplicationConfig.class)

@DisplayName("Тестирование контроллера UserController")
class AuthControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("Тест успешной авторизации")
    public void testLogin_Success() throws Exception {
        String email = "1234@mail.com";
        String password = "password";
        JwtRequest loginRequest = new JwtRequest(email, password);

        JwtResponse expectedResponse = new JwtResponse(1L, email,
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGdtYWlsLmN",
                "eyJhbGciOiJIUzI1NiJ9.lQGdtYWlsLmN");
        when(authService.login(loginRequest)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .secure(true)
                        .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Тест успешного обновления токена")
    void testRefreshToken() throws Exception {
        String refreshToken = "refreshToken123";
        JwtResponse jwtResponse = new JwtResponse(1L, "user", "newToken123", "newRefreshToken123");

        given(authService.refresh(refreshToken)).willReturn(jwtResponse);
        when(authService.refresh(refreshToken)).thenReturn(jwtResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").value("newToken123"))
                .andExpect(jsonPath("refreshToken").value("newRefreshToken123"));

        verify(authService).refresh(refreshToken);
    }


    @Test
    @WithMockUser
    void testRegister() throws Exception {
        UserDto userDto = new UserDto(1L, "user", "email@example.com", "password");
        UserDto savedUserDto = new UserDto(1L, "user", "email@example.com", "password");

        given(userService.create(any(UserDto.class))).willReturn(savedUserDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.email").value("email@example.com"));


    }


}