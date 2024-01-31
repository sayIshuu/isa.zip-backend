package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.repository.UserRepository;
import backend.zip.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisService refreshTokenRedisService;

    public void logout() {
        refreshTokenRedisService.deleteRefreshToken(Long.parseLong(SecurityUtils.getLoggedInUserId()));
    }
}
