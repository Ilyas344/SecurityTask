package t1academy.securitytask.config;


import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import t1academy.securitytask.mappers.JwtMapper;
import t1academy.securitytask.mappers.UserMapper;
import t1academy.securitytask.model.security.JwtProperties;
import t1academy.securitytask.repository.UserRepository;
import t1academy.securitytask.security.JwtTokenProvider;
import t1academy.securitytask.security.JwtUserDetailsService;
import t1academy.securitytask.service.impl.AuthServiceImpl;
import t1academy.securitytask.service.impl.UserServiceImpl;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "dmdqYmhqbmttYmNhamNjZWhxa25hd2puY2xhZWtic3ZlaGtzYmJ1dg=="
        );
        return jwtProperties;
    }

    @Bean
    public UserDetailsService userDetailsService(final UserRepository userRepository) {
        return new JwtUserDetailsService(userService(userRepository));
    }


    @Bean
    public Configuration configuration() {
        return Mockito.mock(Configuration.class);
    }


    @Bean
    public JwtTokenProvider tokenProvider(final UserRepository userRepository) {
        return new JwtTokenProvider(jwtProperties(),
                userDetailsService(userRepository),
                userService(userRepository));
    }


    @Bean
    @Primary
    public AuthServiceImpl authService(final UserRepository userRepository,
                                       final AuthenticationManager authenticationManager) {
        return new AuthServiceImpl(
                authenticationManager,
                userService(userRepository),
                tokenProvider(userRepository),
                jwtMapper()
        );
    }

    @Bean
    @Primary
    public UserServiceImpl userService(final UserRepository userRepository) {
        return new UserServiceImpl(
                userRepository,
                testPasswordEncoder(),
                userMapper()
        );
    }

    @Bean
    public UserMapper userMapper() {
        return Mockito.mock(UserMapper.class);
    }

    @Bean
    public JwtMapper jwtMapper() {
        return Mockito.mock(JwtMapper.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return Mockito.mock(AuthenticationManager.class);
    }

}
