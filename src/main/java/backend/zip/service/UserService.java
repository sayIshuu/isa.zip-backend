package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    // 프로필 조회

    // 프로필 수정

    // 로그아웃
    public void logout();
}
