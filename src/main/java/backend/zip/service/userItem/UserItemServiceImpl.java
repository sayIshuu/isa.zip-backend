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
                                 String address,
                                 String dong,
                                 AddUserItemOptionsRequest addUserItemOptionsRequest) {


        // userItem 생성
        UserItem userItem = UserItem.builder()
                .user(userRepository.findById(userId).orElseThrow())
                .address(address)
                .dong(dong)
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
        //공인중개사가 보는 화면 이니까 자기 userId를 넣어 야겠죠?
        Set<String> dongOfBrokerItem = brokerItemShowService.findDongOfBrokerItem(userId);
        dongCountMap = dongCountMap.entrySet().stream()
                .filter(value
                        -> dongOfBrokerItem.contains(value.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return UserItemDongCountResponse.from(dongCountMap);
    }

    // 동별로 요청된 매물 정보들 전부 조회합니다.
    // 상도동 토글이면 상도동 요청들 다뜨게
    public List<UserItemByDongResponse> getUserItemSortedByDong() {
        List<UserItem> userItems = userItemRepository.findAll();
        Map<String, List<UserItem>> userItemByDong = userItems.stream()
                .collect(Collectors.groupingBy(UserItem::getDong));
        return UserItemByDongResponse.from(userItemByDong);
    }
}