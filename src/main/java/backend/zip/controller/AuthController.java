package backend.zip.controller;

import backend.zip.dto.auth.request.AuthRequest;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.Auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    // 이메일로 회원가입
    @Operation(summary = "이메일로 회원가입", description = "이메일로 회원가입을 진행하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PostMapping("/auth/sign-up")
    public ApiResponse<String> signUp(@RequestBody AuthRequest.SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ApiResponse.onSuccess("회원가입에 성공하셨습니다.");
    }

    // 이메일로 로그인
    //@PostMapping("/auth/login")
    //public ApiResponse<LoginResponse> login() {
    //    return ApiResponse.onSuccess(authService.login());
    //}

    // 이메일 인증번호 요청
    @Operation(summary = "이메일 인증 번호 요청", description = "이메일로 인증 번호를 전송하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PostMapping("/auth/authcode-request")
    public ApiResponse<String> authcodeRequest(@RequestBody AuthRequest.AuthcodeRequest AuthcodeRequest) {
        authService.sendCodeToEmail(AuthcodeRequest.getEmail());
        return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
    }

    // 이메일 인증번호 확인
    @Operation(summary = "이메일 인증 번호 검증", description = "이메일 인증 번호를 검증하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PostMapping("/auth/authcode-check")
    public ApiResponse<String> authcodeCheck(@RequestBody AuthRequest.VerificationRequest verificationRequest) {
        authService.verifyCode(verificationRequest.getEmail(), verificationRequest.getCode());
        return ApiResponse.onSuccess("이메일 인증에 성공하셨습니다.");
    }

    // 카카오로 회원가입
}
