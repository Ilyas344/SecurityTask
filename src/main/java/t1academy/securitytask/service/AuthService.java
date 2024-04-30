package t1academy.securitytask.service;

import t1academy.securitytask.dto.auth.JwtRequest;
import t1academy.securitytask.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

}
