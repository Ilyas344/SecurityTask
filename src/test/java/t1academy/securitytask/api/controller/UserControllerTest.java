package t1academy.securitytask.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import t1academy.securitytask.config.TestConfig;
import t1academy.securitytask.dto.user.UserDto;
import t1academy.securitytask.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование контроллера UserController")
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserService userService;

    @Test
    @DisplayName("Проверка метода getById при успешном выполнении")
    public void testGetTaskById_Success() throws Exception {
        var responseFromService = UserDto.builder()
                .id(1L)
                .email("1234@mai.com")
                .username("Linda")
                .password("1234")
                .build();
        when(userService.getById(1L)).thenReturn(responseFromService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1L)))
                .andExpect(jsonPath("$.email", is("1234@mai.com")))
                .andExpect(jsonPath("$.password", is("1234")))
                .andExpect(jsonPath("$.username", is("Linda")));
        verify(userService, Mockito.times(1)).getById(1L);

    }

}