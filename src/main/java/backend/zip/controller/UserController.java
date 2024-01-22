package backend.zip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    // 프로필 조회
    @GetMapping("/users/{userId}")
    public String getProfile() {
        return null;
    }

    // 프로필 수정
    @PutMapping("/users/{userId}/change-profile")
    public String updateProfile() {
        return null;
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout() {
        return null;
    }

    // 회원 탈퇴
    @DeleteMapping("/users/{userId}")
    public String deleteUser() {
        return null;
    }
}
