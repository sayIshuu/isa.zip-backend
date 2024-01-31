package backend.zip.service.Auth;

import backend.zip.domain.auth.AuthCode;
import backend.zip.domain.user.User;
import backend.zip.dto.auth.request.AuthRequest;
import backend.zip.global.exception.CustomNoSuchAlgorithmException;
import backend.zip.global.exception.auth.AuthcodeException;
import backend.zip.global.exception.auth.DuplicatedEmailException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UserRepository;
import backend.zip.service.AuthCodeRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import static backend.zip.domain.auth.RedisKey.EAUTH;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthCodeRedisService authCodeRedisService;

    @Override
    @Transactional
    public void signUp(AuthRequest.SignUpRequest signUpRequest) {
        User user = signUpRequest.toUser(
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        userRepository.save(user);
    }

    @Override
    public void sendCodeToEmail(String email) {
        checkEmail(email);
        String title = "isa.zip 이메일 인증 번호";
        String authCode = createCode();
        mailService.sendEmail(email, title, authCode);
        authCodeRedisService.setCode(EAUTH + email, authCode);
    }

    @Override
    public void verifyCode(String email, String code) {
        AuthCode redisAuthCode = authCodeRedisService.getCode(EAUTH + email);

        if( redisAuthCode == null || !redisAuthCode.getCode().equals(code)) {
            throw new AuthcodeException(ErrorStatus.INVALID_AUTH_CODE);
        }
    }

    // 이메일 중복 확인
    private void checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) throw new DuplicatedEmailException(ErrorStatus.NO_SUCH_ALGORITHM);
    }

    // 인증번호 생성
    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch(NoSuchAlgorithmException e) {
            throw new CustomNoSuchAlgorithmException(ErrorStatus.NO_SUCH_ALGORITHM);
        }
    }
}
