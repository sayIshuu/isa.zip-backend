package backend.zip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    // 이메일로 로그인
    @PostMapping("/login")
    public String loginByEmail() {
        return null;
    }

    // 카카오로 로그인

}
