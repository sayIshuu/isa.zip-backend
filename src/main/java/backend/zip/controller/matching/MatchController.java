package backend.zip.controller.matching;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import backend.zip.dto.match.request.BrokerItemIdRequest;
import backend.zip.dto.match.request.UpdateMatchingStatusRequest;
import backend.zip.dto.match.response.MatchItemAllByUserResponse;
import backend.zip.dto.match.response.MatchItemListResponse;
import backend.zip.dto.match.response.MatchItemResponse;
import backend.zip.dto.match.response.MatchStatusResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.match.MatchService;
import backend.zip.service.brokeritem.BrokerItemShowService;
import backend.zip.service.userItem.UserItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final BrokerItemShowService brokerItemShowService;

    @Operation(summary = "매칭후보등록", description = "요청 지역에 대한 공인중개사 보유 매물중 후보로 올리기")
    @PostMapping("/brokers/{userItemId}")
    public ApiResponse<MatchItemResponse> matchBrokerItems(@PathVariable Long userItemId,
                                                                 @RequestBody BrokerItemIdRequest brokerItemIdRequest) {

        List<Matching> matchingList = matchService.matchBrokerItemsToUserItem(userItemId, brokerItemIdRequest.getBrokerItemId());
        MatchItemResponse matchItemResponse = MatchItemResponse.of(matchingList);

        return ApiResponse.onSuccess(matchItemResponse);
    }

//    @Operation(summary = "매칭후보삭제", description = "요청 지역에 대한 공인중개사 보유 매물중 후보에서 내리기")
//    @DeleteMapping("/brokers/items")
//    public void unmatchBrokerItems() {
//
//    }

    @Operation(summary = "매물별매칭상태변경", description = "matchStatus인자에 해당값을 넣어주시면 됩니다." +
                                                    "일반유저가 자기 매칭요청상태에서 +버튼 눌러서 찜하기 : MATCH_LIKE" +
                                                    "일반유저가 최종적으로 매칭완료시키기 : MATCH_COMPLETE")
    @PatchMapping("/status") // 엔드포인트 변경필요 비직관적임.
    public ApiResponse<String> matchCompleteBrokerItems(@RequestBody UpdateMatchingStatusRequest updateMatchingStatusRequest) {
        for (Long matchingId : updateMatchingStatusRequest.getMatchingIds()) {
            matchService.updateMatchStatus(matchingId, updateMatchingStatusRequest.getMatchStatus());
        }
        return ApiResponse.onSuccess("매칭상태변경완료");
    }

    
    @Operation(summary = "매칭전체조회", description = "일반유저의 요청에 대해 매칭된 매물 전체 조회")
    @GetMapping("/brokers/items")
    public ApiResponse<MatchItemListResponse> matchUserItems() {
        List<Matching> matchingList = matchService.findAllMatch(brokerItemShowService.checkBroker());
        MatchItemListResponse matchItemListResponse = MatchItemListResponse.of(matchingList);
        return ApiResponse.onSuccess(matchItemListResponse);
    }
    
    @Operation(summary = "유저사이드매칭조회", description = "일반유저의 요청에 대해 매칭된 매물조회 (매칭요청 : WAITING, 매칭완료 : MATCH_COMPLETE)")
    @GetMapping("/users/items")
    public ApiResponse<List<MatchItemAllByUserResponse>> matchUserItems(@RequestParam MatchStatus matchStatus) {
        Long userId = Long.valueOf(SecurityUtils.getLoggedInUserId());
        // 결국 컨트롤러 선에서 서비스 get함수에 넘기는 인자는 데이터 쿼리문 작성을 위한것
        return ApiResponse.onSuccess(matchService.getMatchItemsByStatus(userId, matchStatus));
    }




    //일단은 여기까지먼저.

}
