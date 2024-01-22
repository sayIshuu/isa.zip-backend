package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 프로필 조회
    public User getProfile(Long userId) {
        return userRepository.findById(userId).get();
    }

    // 프로필 수정
}
