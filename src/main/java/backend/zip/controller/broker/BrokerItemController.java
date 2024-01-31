package backend.zip.controller.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;
import backend.zip.dto.brokeritem.response.BrokerItemDetailResponse;
import backend.zip.dto.brokeritem.response.BrokerItemOptionResponse;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.broker.BrokerItemAddressService;
import backend.zip.service.broker.BrokerItemDetailService;
import backend.zip.service.broker.BrokerItemOptionService;
import backend.zip.service.broker.BrokerItemSaveService;
import backend.zip.service.map.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/brokers")
public class BrokerItemController {
    private final BrokerItemAddressService brokerItemAddressService;
    private final BrokerItemDetailService brokerItemDetailService;
    private final BrokerItemOptionService brokerItemOptionService;
    private final BrokerItemSaveService brokerItemSaveService;
    private final AddressService addressService;

    /**
     * 주소 입력 받으면 주소, 동, x좌표, y좌표 저장
     */
    @GetMapping(value = "/{userId}/map", produces = "application/json;charset=UTF-8")
    public ApiResponse<BrokerItemAddressResponse> saveAddress(@PathVariable Long userId,
                                                              @RequestParam("address") String roadFullAddress) {

        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        BrokerItemAddressResponse brokerItemAddressResponse = addressService.returnAddressAndDongAndXY(kaKaoApiFromInputAddress);

        brokerItemAddressService.saveBrokerItemAddress(userId,brokerItemAddressResponse.getAddress()
                , brokerItemAddressResponse.getDong(), brokerItemAddressResponse.getX(), brokerItemAddressResponse.getY());

        return ApiResponse.onSuccess(brokerItemAddressResponse);
    }

//    /**
//     * 옵션 입력 받으면 옵션 저장
//     */
//    @PostMapping("/items/{brokerItemId}/options")
//    public ApiResponse<BrokerItemOptionResponse> saveOptions(@PathVariable Long brokerItemId,
//                                                             @RequestBody AddBrokerItemOptionsRequest addBrokerItemOptionsRequest) {
//
//        BrokerOption brokerOption = brokerItemOptionService.saveBrokerItemOptions(brokerItemId, addBrokerItemOptionsRequest);
//        BrokerItemOptionResponse brokerItemOptionResponse = new BrokerItemOptionResponse(brokerOption);
//
//        return ApiResponse.onSuccess(brokerItemOptionResponse);
//    }

    /**
     * 매물 올리기(공인중개사 입장에서 저장)
     */
    @PostMapping(value = "/{userId}/items",produces = "application/json;charset=UTF-8")
    public ApiResponse<BrokerItemResponse> completeRegistration(@PathVariable Long userId,
                                                                @RequestParam("address") String roadFullAddress,
                                                                @RequestPart("detailsRequest") AddBrokerItemDetailsRequest detailsRequest,
                                                                @RequestPart("optionsRequest") AddBrokerItemOptionsRequest optionsRequest,
                                                                @RequestPart("multipartFiles") MultipartFile[] multipartFiles) {
        /**
         * 주소,동 ,x, y 좌표 저장
         */
        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        BrokerItemAddressResponse addressResponse = addressService.returnAddressAndDongAndXY(kaKaoApiFromInputAddress);
        BrokerItem savedBrokerItem = brokerItemAddressService.saveBrokerItemAddress(userId, addressResponse.getAddress()
                , addressResponse.getDong(), addressResponse.getX(), addressResponse.getY()); //주소만 저장된 매물 아이템

        MultipartFile[] brokerItemImg = detailsRequest.getBrokerItemImg();
        brokerItemImg = multipartFiles;

        // 주소 저장과 디테일, 옵션 저장 로직을 하나의 트랜잭션으로 실행
        savedBrokerItem = brokerItemSaveService.saveBrokerItem(userId,savedBrokerItem,
                addressResponse.getAddress(), addressResponse.getDong(), addressResponse.getX(), addressResponse.getY(),
                detailsRequest,
                brokerItemImg,
                optionsRequest
        );

        BrokerItemResponse itemResponse = getBrokerItemResponse(savedBrokerItem, addressResponse);

        // 성공 응답 반환
        return ApiResponse.onSuccess(itemResponse);
    }

    private static BrokerItemResponse getBrokerItemResponse(BrokerItem savedBrokerItem, BrokerItemAddressResponse addressResponse) {
        // BrokerDetailResponse 생성
        List<ItemImage> itemImages = savedBrokerItem.getItemImages();
        ItemContent itemContent = savedBrokerItem.getItemContent();
        BrokerItemDetailResponse detailResponse = new BrokerItemDetailResponse(itemImages, itemContent);

        // BrokerOptionResponse 생성
        BrokerOption brokerOption = savedBrokerItem.getBrokerOption();
        BrokerItemOptionResponse optionResponse = new BrokerItemOptionResponse(brokerOption);

        // BrokerItemResponse 생성
        BrokerItemResponse itemResponse = new BrokerItemResponse(addressResponse, detailResponse, optionResponse);

        return itemResponse;
    }
}




