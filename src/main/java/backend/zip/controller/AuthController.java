package backend.zip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    // 이메일로 회원가입
    @PostMapping("/users/register/email")
    public String resiterByEmail() {
        return null;
    }

    // 이메일 중복 확인
    @PostMapping("/users/check-email")
    public String checkEmail() {
        return null;
    }

    // 닉네임 중복 확인
    @PostMapping("/users/check-nickname")
    public String checkNickname() {
        return null;
    }

    // 카카오로 회원가입
}
