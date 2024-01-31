package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.global.exception.user.UserNotFoundException;
import backend.zip.repository.UserRepository;
import backend.zip.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static backend.zip.global.status.ErrorStatus.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisService refreshTokenRedisService;

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
