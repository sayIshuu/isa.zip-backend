package backend.zip.controller;

import backend.zip.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @Operation(summary = "매칭후보등록", description = "요청 지역에 대한 공인중개사 보유 매물중 후보로 올리기")
    @PostMapping("/brokers/items")
    public void matchBrokerItems() {

    }

    @Operation(summary = "매칭후보삭제", description = "요청 지역에 대한 공인중개사 보유 매물중 후보에서 내리기")
    @DeleteMapping("/brokers/items")
    public void unmatchBrokerItems() {

    }

    @Operation(summary = "매물별매칭완료", description = "매칭 후보에서 매칭 확정시켜서 요청한 사용자가 조회 가능하게 하기")
    @PatchMapping("/brokers/items/{itemId}")
    public void matchCompleteBrokerItems() {

    }

    @Operation(summary = "매칭전체조회", description = "일반유저의 요청에 대해 매칭된 매물 전체 조회")
    @GetMapping("/users/items")
    public void matchUserItems() {

    }

    //일단은 여기까지먼저.

}
