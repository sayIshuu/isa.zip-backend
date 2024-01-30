package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.dto.auth.request.AuthRequest;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(AuthRequest.SignUpRequest signUpRequest) {
        User user = signUpRequest.toUser(
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        userRepository.save(user);
    }
}
