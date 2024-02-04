package backend.zip.controller;

import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.dto.useritem.response.UserItemAddressResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.userItem.UserItemServiceImpl;
import backend.zip.service.map.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserItemController {

    private final UserItemServiceImpl userItemService;
    private final AddressService addressService;
    /*
    @GetMapping("/items")
    public List<UserItem> getAllUserItems() {
        return userItemService.getAllUserItems();
    }

    @GetMapping("{userId}/items")
    public List<UserItem> getUserItemByUserId(@PathVariable Long userId) {
        return userItemService.getUserItemByUserId(userId);
    }
    */

    @Operation(summary = "유저 매물 요청정보 저장", description = "유저가 요청한 매물 정보를 저장합니다.")
    @PostMapping("{userId}/items")
    public ApiResponse<String> saveUserItem(@RequestParam("address") String address,
                                            @RequestBody AddUserItemOptionsRequest addUserItemOptionsRequest) {

        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);

        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(address);
        UserItemAddressResponse addressResponse = addressService.returnUserItemAddressAndDong(kaKaoApiFromInputAddress);

        userItemService.saveUserItem(userId,
                                    addressResponse.getAddress(),
                                    addressResponse.getDong(),
                                    addUserItemOptionsRequest);

        // 일단은 response dto 없이 성공 메시지만 반환, 추후 프론트에서 필요하면 response dto 추가
        return ApiResponse.onSuccess("유저 아이템 저장에 성공하셨습니다.");
    }

}