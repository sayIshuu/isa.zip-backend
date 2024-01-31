package backend.zip.service.Auth;

import backend.zip.dto.auth.request.AuthRequest;
import backend.zip.dto.auth.response.AuthResponse;


public interface AuthService {
    void signUp(AuthRequest.SignUpRequest signUpRequest);

    void sendCodeToEmail(String email);

    void verifyCode(String email, String code);

    AuthResponse.LoginResponse login(AuthRequest.LoginRequest loginRequest);
}
