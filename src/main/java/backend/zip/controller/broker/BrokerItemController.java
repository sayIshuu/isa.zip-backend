package backend.zip.controller.broker;

import backend.zip.config.AddressConfig;
import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.*;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.global.status.SuccessStatus;
import backend.zip.security.SecurityUtils;
import backend.zip.service.brokeritem.BrokerItemAddressService;
import backend.zip.service.brokeritem.BrokerItemService;
import backend.zip.service.brokeritem.BrokerItemShowService;
import backend.zip.service.map.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final BrokerItemShowService brokerItemShowService;
    private final BrokerItemService brokerItemService;
    private final AddressService addressService;

    @Operation(summary = "매물 새로 등록 시 Step 1 주소 입력 하기", description = "주소를 입력하면 관련된 주소를 반환해 줍니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @GetMapping(value = "/map", produces = "application/json;charset=UTF-8")
    public ApiResponse<List<AddressResponse>> returnAddress(@RequestParam("address") String roadFullAddress) {
        brokerItemShowService.checkBroker();
        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        List<AddressResponse> addressResponses = AddressConfig.extractAddress(kaKaoApiFromInputAddress);
        return ApiResponse.onSuccess(addressResponses);
    }


    @Operation(summary = "매물 새로 등록 시 Step1 & 2 & 3", description = "공인중개사가 매물을 새로 등록할 수 있습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PostMapping(value = "/items", consumes = "multipart/form-data")
    public ApiResponse<BrokerItemResponse> registerBrokerItem(@RequestParam("address") String roadFullAddress,
                                                              @RequestPart(value = "detailsRequest",required = false) AddBrokerItemDetailsRequest detailsRequest,
                                                              @RequestPart(value = "optionsRequest",required = false) AddBrokerItemOptionsRequest optionsRequest,
                                                              @RequestPart(value = "multipartFiles",required = false) MultipartFile[] multipartFiles) {

        Long userId = brokerItemShowService.checkBroker();
        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        BrokerItemAddressResponse addressResponse = addressService.returnAddressInfo(kaKaoApiFromInputAddress);
        BrokerItem savedBrokerItem = brokerItemAddressService.saveBrokerItemAddress(brokerItemShowService.checkBroker(), addressResponse);
        BrokerItem brokerItem = brokerItemService.saveBrokerItem(userId, savedBrokerItem, addressResponse, detailsRequest, multipartFiles, optionsRequest);
        BrokerItemResponse brokerItemResponse = getBrokerItemResponse(brokerItem, addressResponse);

        return ApiResponse.onSuccess(brokerItemResponse);
    }

    @Operation(summary = "매물을 삭제할 수 있습니다.", description = "공인중개사가 매물을 삭제 할 수 있습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @DeleteMapping(value = "/items/{brokerItemId}")
    public ApiResponse<SuccessStatus> deleteBrokerItem(@PathVariable Long brokerItemId) {
        brokerItemService.deleteBrokerItem(brokerItemId);

        return ApiResponse.onSuccess(SuccessStatus._OK);
    }

    @Operation(summary = "매물 정보 업데이트", description = "매물의 정보를 업데이트합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PutMapping(value = "/items/{brokerItemId}")
    public ApiResponse<BrokerItemResponse> updateBrokerItem(@PathVariable Long brokerItemId,
                                                            @RequestParam(value = "address",required = false) String roadFullAddress,
                                                            @RequestPart(value = "detailsRequest",required = false) AddBrokerItemDetailsRequest detailsRequest,
                                                            @RequestPart(value = "optionsRequest",required = false) AddBrokerItemOptionsRequest optionsRequest,
                                                            @RequestPart(value = "multipartFiles",required = false) MultipartFile[] multipartFiles) {

        BrokerItem updateBrokerItem = brokerItemService.updateBrokerItem(brokerItemId, roadFullAddress, detailsRequest, optionsRequest, multipartFiles);
        BrokerItemResponse brokerItemResponse = BrokerItemResponse.of(updateBrokerItem);

        return ApiResponse.onSuccess(brokerItemResponse);
    }

    @Operation(summary = "매물 SOLD OUT", description = "매물을 판매 완료 상태로 변경 합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @PatchMapping(value = "/items/{brokerItemId}/soldout")
    public ApiResponse<BrokerItemStatusResponse> makeSoldOut(@PathVariable Long brokerItemId) {
        BrokerItem soldBrokerItem = brokerItemService.makeBrokerItemSoldOut(brokerItemId);
        BrokerItemStatusResponse brokerItemStatusResponse = BrokerItemStatusResponse.of(soldBrokerItem);

        return ApiResponse.onSuccess(brokerItemStatusResponse);
    }


}




