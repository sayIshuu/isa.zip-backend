package backend.zip.service.userItem;

import backend.zip.domain.user.User;
import backend.zip.domain.user.UserItem;
import backend.zip.domain.user.UserOption;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.dto.useritem.response.UserItemByDongResponse;
import backend.zip.dto.useritem.response.UserItemDongCountResponse;
import backend.zip.repository.UserItemRepository;
import backend.zip.repository.UserRepository;
import backend.zip.service.brokeritem.BrokerItemShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserItemServiceImpl implements UserItemService{
    private final UserItemRepository userItemRepository;
    private final UserItemOptionService userItemOptionService;
    private final BrokerItemShowService brokerItemShowService;
    private final UserRepository userRepository;

    public UserItem saveUserItem(Long userId,
                                 String dong,
                                 AddUserItemOptionsRequest addUserItemOptionsRequest) {


        // userItem 생성
        UserItem userItem = UserItem.builder()
                .user(userRepository.findById(userId).orElseThrow())
                .dong(dong)
                .isMatched(false)
                .build();

        // 옵션 저장 현수님 방법 차용
        UserOption userOption = userItemOptionService.saveUserItemOptions(addUserItemOptionsRequest);
        // userItem에 userOption을 저장
        userItem.setUserOption(userOption);
        userItemRepository.save(userItem);

        return userItem;
    }

    // 지역별 요청 개수 확인
    // 근데 로그인된 공인중개사 유저가 가지고 있는 매물에 해당하는 지역(동)만 뜨게
    public List<UserItemDongCountResponse> getUserItemDongCount(Long userId) {
        List<String> dongList = userItemRepository.findAllDongs();
        Map<String, Long> dongCountMap = dongList.stream()
                .collect(Collectors.groupingBy(dong -> dong, Collectors.counting()));

        // 공인중개사가 보는 화면 이므로 자기 userId를 넣어야합니다.
        Set<String> dongOfBrokerItem = brokerItemShowService.findDongOfBrokerItem(userId);

        // dongOfBrokerItem에 있는 동 이름이 dongCountMap에 없는 경우를 확인하고 0으로 초기화하여 추가합니다.
        for (String dong : dongOfBrokerItem) {
            dongCountMap.putIfAbsent(dong, 0L);
        }

        // dongCountMap에는 있지만 dongOfBrokerItem에는 없는 동 이름을 제거합니다.
        dongCountMap.keySet().retainAll(dongOfBrokerItem);

        return UserItemDongCountResponse.from(dongCountMap);
    }

    /*
    public List<UserItemDongCountResponse> getUserItemDongCount(Long userId) {
        List<String> dongList = userItemRepository.findAllDongs();
        Map<String, Long> dongCountMap = dongList.stream()
                .collect(Collectors.groupingBy(dong -> dong, Collectors.counting()));
        //공인중개사가 보는 화면 이니까 자기 userId를 넣어 야겠죠? 보유 매물 dong이름들 모음set
        Set<String> dongOfBrokerItem = brokerItemShowService.findDongOfBrokerItem(userId);
        dongCountMap = dongCountMap.entrySet().stream()
                .filter(value
                        -> dongOfBrokerItem.contains(value.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return UserItemDongCountResponse.from(dongCountMap);
    }
     */

    // 동별로 요청된 매물 정보들 전부 조회합니다.
    // 상도동 토글이면 상도동 요청들 다뜨게
    public UserItemByDongResponse getUserItemSortedByDong(String dongName) {
        List<UserItem> userItems = userItemRepository.findAllByDong(dongName);
        //Map<String, List<UserItem>> userItemByDong = userItems.stream()
        //        .collect(Collectors.groupingBy(UserItem::getDong));
        //만약 userItems의 isMatched가 true이면 리스트에서 제외하고 보냄
        userItems = userItems.stream()
                .filter(userItem -> !userItem.getIsMatched())
                .collect(Collectors.toList());
        return UserItemByDongResponse.from(dongName, userItems);
    }
}