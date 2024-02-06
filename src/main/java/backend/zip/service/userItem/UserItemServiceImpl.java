package backend.zip.service.userItem;

import backend.zip.domain.user.UserItem;
import backend.zip.domain.user.UserOption;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.dto.useritem.response.UserItemByDongResponse;
import backend.zip.dto.useritem.response.UserItemDongCountResponse;
import backend.zip.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class UserItemServiceImpl implements UserItemService{
    private final UserItemRepository userItemRepository;
    private final UserItemOptionService userItemOptionService;

    public UserItem saveUserItem(Long userId,
                             String address,
                             String dong,
                             AddUserItemOptionsRequest addUserItemOptionsRequest) {

        UserItem userItem = UserItem.builder()
                .userItemId(userId)
                .address(address)
                .dong(dong)
                .build();

        // 옵션 저장 로직
        // 유저 옵션을 만들어서 useritem에 저장해줘야함.
        /* 왜 안될까요 리스트라서?
        UserOption userOption = UserOption.builder()
                .userItem(userItem)
                .userRoomTypes(addUserItemOptionsRequest.getRoomType())
                .dealInfoMap(addUserItemOptionsRequest.getDealInfoMap())
                .roomSize(addUserItemOptionsRequest.getRoomSize())
                .floor(addUserItemOptionsRequest.getFloor())
                .managementOption(addUserItemOptionsRequest.getManagementOption())
                .internalFacility(addUserItemOptionsRequest.getInternalFacility())
                .approveDate(addUserItemOptionsRequest.getApproveDate())
                .extraFilter(addUserItemOptionsRequest.getExtraFilter())
                .build();
        */

        // 옵션 저장 현수님 방법 차용
        UserOption userOption = userItemOptionService.saveUserItemOptions(addUserItemOptionsRequest);

        // userItem에 userOption을 저장
        userItem.setUserOption(userOption);
        userItemRepository.save(userItem);

        return userItem;
    }

    // 지역별 요청 개수 확인
    // 근데 로그인된 공인중개사 유저가 가지고 있는 매물에 해당하는 지역(동)만 뜨게
    public List<UserItemDongCountResponse> getUserItemDongCount() {
        List<String> dongList = userItemRepository.findAllDongs();
        Map<String, Long> dongCountMap = dongList.stream()
                .collect(Collectors.groupingBy(dong -> dong, Collectors.counting()));
        // =====현수님 필터작업 하나 해놓으시면 됩니다.=======
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