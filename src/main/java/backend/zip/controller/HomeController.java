package backend.zip.controller;

import backend.zip.dto.home.response.HomeResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    @GetMapping("/home")
    public ApiResponse<HomeResponse.ShowHomeResponse> showHome() {
        return ApiResponse.onSuccess(homeService.showHome());
    }
}
