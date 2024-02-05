package backend.zip.controller.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.broker.BrokerItemShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static backend.zip.dto.brokeritem.response.BrokerItemResponse.getBrokerItemResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/brokers")
public class BrokerItemListController {
    private final BrokerItemShowService brokerItemShowService;

    @Operation(summary = "공인중개사 매물관리 페이지에서 매물 전체 조회", description = "공인중개사 자신이 가지고 있는 매물 전체를 보여줍니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @GetMapping(value = "items/show")
    public ApiResponse<List<BrokerItemResponse>> findBrokerItemList() {
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);

        List<BrokerItem> brokerItemList = brokerItemShowService.findBrokerItemList(userId);
        List<BrokerItemResponse> findAllBrokerItemList = brokerItemList.stream()
                .map(brokerItem -> getBrokerItemResponse(brokerItem))
                .collect(Collectors.toList());


        return ApiResponse.onSuccess(findAllBrokerItemList);
    }

    @Operation(summary = "공인중개사 매물관리 페이지에서 매물 조회", description = "공인중개사 자신이 가지고 있는 매물 전체를 보여줍니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공")
    })
    @GetMapping(value = "items/show/{brokerItemId}")
    public ApiResponse<BrokerItemResponse> findBrokerItem(@PathVariable Long brokerItemId) {
        BrokerItem findBrokerItem = brokerItemShowService.findBrokerItem(brokerItemId);
        BrokerItemResponse brokerItemResponse = getBrokerItemResponse(findBrokerItem);

        return ApiResponse.onSuccess(brokerItemResponse);
    }

}
