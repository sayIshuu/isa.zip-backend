package backend.zip.service;

import backend.zip.domain.s3.Uuid;
import backend.zip.domain.user.User;
import backend.zip.dto.auth.response.AuthResponse;
import backend.zip.dto.user.request.UserRequest;
import backend.zip.dto.user.response.UserResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.global.aws.s3.AmazonS3Manager;
import backend.zip.global.exception.user.UserNotFoundException;
import backend.zip.repository.UserRepository;
import backend.zip.repository.UuidRepository;
import backend.zip.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static backend.zip.global.status.ErrorStatus.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final PasswordEncoder passwordEncoder;
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    public UserResponse.ProfileResponse getProfile() {
        User user = userRepository.findById(Long.parseLong(SecurityUtils.getLoggedInUserId()))
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
        return UserResponse.ProfileResponse.builder()
                .id(user.getId())
                .userImg(user.getUserImg())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponse.ProfileResponse updateProfile(MultipartFile userImg, String nickName, String password) {
        User user = userRepository.findById(Long.parseLong(SecurityUtils.getLoggedInUserId()))
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));

        String url = user.getUserImg();

        if(userImg != null && !userImg.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
            url = amazonS3Manager.uploadFile(amazonS3Manager.generateUserKeyName(savedUuid), userImg);
            user.updateUserImg(url);
        }

        if(nickName != null && !nickName.isEmpty()) {
            user.updateNickName(nickName);
        }

        if(password != null && !password .isEmpty()) {
            user.updatePassword(passwordEncoder.encode(password));
        }

        return UserResponse.ProfileResponse.builder()
                .id(user.getId())
                .userImg(url)
                .nickName(user.getNickName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void logout() {
        refreshTokenRedisService.deleteRefreshToken(Long.parseLong(SecurityUtils.getLoggedInUserId()));
    }

    @Override
    public void deleteUser() {
        User user = userRepository.findById(Long.parseLong(SecurityUtils.getLoggedInUserId()))
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));

        refreshTokenRedisService.deleteRefreshToken(user.getId());
        userRepository.delete(user);
    }
}
