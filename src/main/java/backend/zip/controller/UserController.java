package backend.zip.controller;

import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
    @Operation(summary = "로그아웃", description = "로그아웃하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PostMapping("/users/logout")
    public ApiResponse<String> logout() {
        userService.logout();
        return ApiResponse.onSuccess("로그아웃에 성공하셨습니다.");
    }

    // 회원 탈퇴
    @DeleteMapping("/users/{userId}")
    public String deleteUser() {
        return null;
    }
}
