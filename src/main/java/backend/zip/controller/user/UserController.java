package backend.zip.controller.user;

import backend.zip.dto.user.request.UserRequest;
import backend.zip.dto.user.response.UserResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "프로필을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    // 프로필 조회
    @GetMapping("/users")
    public ApiResponse<UserResponse.ProfileResponse> getProfile() {
        return ApiResponse.onSuccess(userService.getProfile());
    }

    // 프로필 수정
    @PutMapping("/users")
    public ApiResponse<UserResponse.ProfileResponse> updateProfile(@RequestPart(required = false) MultipartFile userImg, @RequestPart(required = false) String nickName, @RequestPart(required = false) String password) {
        return ApiResponse.onSuccess("프로필이 수정되었습니다.", userService.updateProfile(userImg, nickName, password));
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
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @DeleteMapping("/users")
    public ApiResponse<String> deleteUser() {
        userService.deleteUser();
        return ApiResponse.onSuccess("회원 탈퇴에 성공하셨습니다.");
    }

    // 공인중개사 인증
    @PostMapping("/users/auth-broker")
    public ApiResponse<String> authenticateBroker(@RequestBody UserRequest.BrokerRequest brokerRequest) {
        userService.authenticateBroker(brokerRequest);
        return ApiResponse.onSuccess("공인중개사 인증이 완료되었습니다.");
    }
}
