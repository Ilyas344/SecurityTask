package t1academy.securitytask.security;

import t1academy.securitytask.model.user.User;
import t1academy.securitytask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        User user = userService.getByEmail(email);
        return JwtEntityFactory.create(user);
    }

}
