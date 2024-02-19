package backend.zip.service.auth;

import backend.zip.domain.auth.TokenInfo;
import backend.zip.domain.auth.AuthCode;
import backend.zip.domain.user.User;
import backend.zip.dto.auth.request.AuthRequest;
import backend.zip.dto.auth.response.AuthResponse;
import backend.zip.global.exception.auth.CustomNoSuchAlgorithmException;
import backend.zip.global.exception.auth.AuthcodeException;
import backend.zip.global.exception.auth.DuplicatedEmailException;
import backend.zip.global.exception.user.UserNotFoundException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.user.UserRepository;
import backend.zip.security.JwtTokenProvider;
import backend.zip.service.token.AuthCodeRedisService;
import backend.zip.service.token.RefreshTokenRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import static backend.zip.domain.enums.RedisKey.EAUTH;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthCodeRedisService authCodeRedisService;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signUp(AuthRequest.SignUpRequest signUpRequest) {
        checkEmail(signUpRequest.getEmail());

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

    @Override
    public AuthResponse.LoginResponse login(AuthRequest.LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UserNotFoundException(ErrorStatus.USER_NOT_FOUND));

        TokenInfo tokenInfo = setFirstAuthentication(user.getId(), loginRequest.getPassword());
        refreshTokenRedisService.saveRefreshToken(user.getId(), tokenInfo.getRefreshToken());

        return AuthResponse.LoginResponse.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();
    }

    @Override
    @Transactional
    public AuthResponse.LoginResponse kakaoLogin(AuthRequest.KakaoLoginRequest kakaoLoginRequest) {
        Optional<User> existedUser = userRepository.findBySocialId(kakaoLoginRequest.getSocialId());

        User user;
        if(existedUser.isPresent()) {
            user = existedUser.get();
        } else {
            checkEmail(kakaoLoginRequest.getEmail());

            user = kakaoLoginRequest.toUser(passwordEncoder.encode(kakaoLoginRequest.getSocialId()));
            userRepository.save(user);
        }

        TokenInfo tokenInfo = setFirstAuthentication(user.getId(), kakaoLoginRequest.getSocialId());
        refreshTokenRedisService.saveRefreshToken(user.getId(), tokenInfo.getRefreshToken());

        return AuthResponse.LoginResponse.builder()
                .id(user.getId())
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();
    }

    // 이메일 중복 확인
    private void checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) throw new DuplicatedEmailException(ErrorStatus.DUPLICATED_EMAIL);
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

    private TokenInfo setFirstAuthentication(Long id, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id, password);
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        return jwtTokenProvider.generationToken(authentication);
    }

    private String createRandomPassword() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}
