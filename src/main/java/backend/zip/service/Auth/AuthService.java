package backend.zip.service.Auth;

import backend.zip.dto.auth.request.AuthRequest;


public interface AuthService {
    void signUp(AuthRequest.SignUpRequest signUpRequest);

    void sendCodeToEmail(String email);
}
