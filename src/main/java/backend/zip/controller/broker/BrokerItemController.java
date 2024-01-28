package backend.zip.controller.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;
import backend.zip.dto.brokeritem.response.BrokerItemOptionResponse;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.broker.BrokerItemAddressService;
import backend.zip.service.broker.BrokerItemOptionService;
import backend.zip.service.broker.BrokerItemSaveService;
import backend.zip.service.map.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/brokers")
public class BrokerItemController {
    private final BrokerItemAddressService brokerItemAddressService;
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

    /**
     * 옵션 입력 받으면 옵션 저장
     */
    @PostMapping("/items/{brokerItemId}/options")
    public ApiResponse<BrokerItemOptionResponse> saveOptions(@PathVariable Long brokerItemId,
                                                             @RequestBody AddBrokerItemOptionsRequest addBrokerItemOptionsRequest) {

        BrokerOption brokerOption = brokerItemOptionService.saveBrokerItemOptions(brokerItemId, addBrokerItemOptionsRequest);
        BrokerItemOptionResponse brokerItemOptionResponse = new BrokerItemOptionResponse(brokerOption);

        return ApiResponse.onSuccess(brokerItemOptionResponse);
    }
}




