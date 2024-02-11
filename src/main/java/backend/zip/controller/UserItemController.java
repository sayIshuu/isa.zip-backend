package backend.zip.controller;

import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.dto.useritem.response.UserItemAddressResponse;
import backend.zip.dto.useritem.response.UserItemByDongResponse;
import backend.zip.dto.useritem.response.UserItemDongCountResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.brokeritem.BrokerItemShowService;
import backend.zip.service.userItem.UserItemServiceImpl;
import backend.zip.service.map.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserItemController {
    private final BrokerItemShowService brokerItemShowService;
    private final UserItemServiceImpl userItemService;
    private final AddressService addressService;


    // 추후 공인중개사가 가지고 있는 지역의 매물 요청만 뜨게끔 broker코드와 조율해야합니다.
    // 리스폰스 dto 따로 만들어서 동 저장, 동당 건수 세는 로직으로 저장해서 리턴
    //상도1동 안뜨던거
    @Operation(summary = "유저 매물 요청 동별로 조회", description = "모든 유저가 요청한 매물 요청들의 동,동당건수를 조회합니다.")
    @GetMapping("/items/dong-count")
    public ApiResponse<List<UserItemDongCountResponse>> getUserItemDongCount() {
        Long userId = brokerItemShowService.checkBroker();
        return ApiResponse.onSuccess(userItemService.getUserItemDongCount(userId));
    }

    @Operation(summary = "유저 매물 요청 전체조회", description = "동별로 요청된 매물 정보를 조회합니다. 동이름만 띄워쓰기없이 넣어주세요 ex)상도동")
    @GetMapping("/items")
    public ApiResponse<UserItemByDongResponse> getUserItemSortedByDong(@RequestParam("dongName") String dongName) {
        return ApiResponse.onSuccess(userItemService.getUserItemSortedByDong(dongName));
    }

    /*
    @GetMapping("{userId}/items")
    public List<UserItem> getUserItemByUserId(@PathVariable Long userId) {
        return userItemService.getUserItemByUserId(userId);
    }
    */

    //유저옵션 테이블에 유저아이템 아이디 안들어가는거 수정
    @Operation(summary = "유저 매물 요청정보 저장", description = "유저가 요청한 매물 정보를 저장합니다.")
    @PostMapping("/items")
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