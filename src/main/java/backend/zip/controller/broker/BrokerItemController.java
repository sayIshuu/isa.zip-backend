package backend.zip.controller.broker;

import backend.zip.config.AddressConfig;
import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.enums.Role;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.*;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.global.exception.map.AddressException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UserRepository;
import backend.zip.security.JwtTokenProvider;
import backend.zip.security.SecurityUtils;
import backend.zip.service.broker.BrokerItemAddressService;
import backend.zip.service.broker.BrokerItemDetailService;
import backend.zip.service.broker.BrokerItemOptionService;
import backend.zip.service.broker.BrokerItemSaveService;
import backend.zip.service.map.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static backend.zip.dto.brokeritem.response.BrokerItemResponse.getBrokerItemResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/brokers")
public class BrokerItemController {
    private final BrokerItemAddressService brokerItemAddressService;
    private final BrokerItemSaveService brokerItemSaveService;
    private final AddressService addressService;

    @Operation(summary = "매물 새로 등록 시 Step 1 주소 입력 하기", description = "주소를 입력하면 관련된 주소를 반환해 줍니다.")
    @GetMapping(value = "/map", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<AddressResponse>> returnAddress(@RequestParam("address") String roadFullAddress) {
        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        List<AddressResponse> addressResponses = AddressConfig.extractAddress(kaKaoApiFromInputAddress);
        return ApiResponse.onSuccess(addressResponses);
    }


    @Operation(summary = "매물 새로 등록 시 Step 2 & 3", description = "공인중개사가 매물을 새로 등록할 수 있습니다.")
    @PostMapping(value = "/items", produces = "application/json;charset=UTF-8")
    public ApiResponse<BrokerItemResponse> registerBrokerItem(@RequestParam("address") String roadFullAddress,
                                                                @RequestPart("detailsRequest") AddBrokerItemDetailsRequest detailsRequest,
                                                                @RequestPart("optionsRequest") AddBrokerItemOptionsRequest optionsRequest,
                                                                @RequestPart("multipartFiles") MultipartFile[] multipartFiles) {

        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);

        //주소만 저장된 매물 아이템
        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        BrokerItemAddressResponse addressResponse = addressService.returnAddressInfo(kaKaoApiFromInputAddress);
        BrokerItem savedBrokerItem = brokerItemAddressService.saveBrokerItemAddress(userId, addressResponse.getAddressName()
                , addressResponse.getDong(), addressResponse.getX(), addressResponse.getY());



        // 주소 저장과 디테일, 옵션 저장 로직을 하나의 트랜잭션으로 실행
        BrokerItem brokerItem = brokerItemSaveService.saveBrokerItem(userId, savedBrokerItem, addressResponse, detailsRequest, multipartFiles, optionsRequest);
        BrokerItemResponse brokerItemResponse = getBrokerItemResponse(brokerItem, addressResponse);

        return ApiResponse.onSuccess(brokerItemResponse);
    }

}




