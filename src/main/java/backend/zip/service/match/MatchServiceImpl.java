package backend.zip.service.match;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import backend.zip.domain.user.UserItem;
import backend.zip.dto.match.response.MatchItemAllByUserResponse;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.exception.match.MatchingException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.MatchRepository;
import backend.zip.repository.UserItemRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static backend.zip.domain.enums.MatchStatus.MATCH_COMPLETE;
import static backend.zip.domain.enums.MatchStatus.WAITING;

@Transactional
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final BrokerItemRepository brokerItemRepository;
    private final UserItemRepository userItemRepository;


    @Override // 매칭 후보 등록
    public List<Matching> matchBrokerItemsToUserItem(Long userItemId, List<Long> brokerItemId) {
        List<BrokerItem> brokerItemList = brokerItemId.stream()
                .map(itemId -> brokerItemRepository.findById(itemId)
                        .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND)))
                .collect(Collectors.toList());

        List<Matching> matchingList = new ArrayList<>();
        for (BrokerItem brokerItem : brokerItemList) {
            Matching matching = Matching.createMatching(userItemRepository.findById(userItemId).get(), brokerItem);
            matchingList.add(matching);
            matchRepository.save(matching);
        }
        return matchingList;
    }

    @Override
    public Matching findMatch(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchingException(ErrorStatus.MATCH_NOT_FOUND));
    }


    @Override
    public Matching updateMatchStatus(Long matchId, MatchStatus matchStatus) {
        Matching matching = findMatch(matchId);
        //안전한 방법은 아니지만 프론트에서 화면당 구분하여 호출한다면 예외발생날일은 없을거같은데요
        matching.updateMatchStatus(matchStatus);
        matchRepository.save(matching);
        return matching;
    }

    @Override
    public List<Matching> findAllMatch() {
        return matchRepository.findAll();
    }

    public void unmatchBrokerItems() {
        // 매칭 후보 삭제
    }

    public void matchCompleteBrokerItems() {
        // 매칭 완료
    }

    public List<MatchItemAllByUserResponse> getMatchItemsByStatus(Long userId, MatchStatus matchStatus) {
        // 웨이팅 매칭 객체들 받아와 (유저 요청 id랑 중개사 매물 id 들어있는 ) 이 리스트에 들어있는건 매칭아이템 하나하나일뿐
        List<Matching> matchings = matchRepository.findByUserItemUserIdAndMatchStatus(userId, matchStatus);
        // 요청별로 리스트 나누기 (반환 리스펀스는 요청 과 매칭된 매물들 그래서 각 리스펀스는 맵형태로 저장)
        Map<UserItem, List<Matching>> matchingsByUserItem = matchings.stream() // userRequst로 이름 다 바꾸고 싶다 기능구현 끝나고 리팩토링..
                .collect(Collectors.groupingBy(Matching::getUserItem));
        return MatchItemAllByUserResponse.from(matchingsByUserItem);
    }

}
