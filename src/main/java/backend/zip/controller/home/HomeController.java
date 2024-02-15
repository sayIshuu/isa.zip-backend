package backend.zip.controller.home;

import backend.zip.dto.home.response.HomeResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.home.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    @Operation(summary = "홈 탭 메인 조회", description = "홈 탭 메인을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @GetMapping("/home")
    public ApiResponse<HomeResponse.ShowHomeResponse> showHome() {
        return ApiResponse.onSuccess(homeService.showHome());
    }
}
