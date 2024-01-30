package backend.zip.service;

import backend.zip.dto.auth.request.AuthRequest;


public interface AuthService {
    void signUp(AuthRequest.SignUpRequest signUpRequest);
}
