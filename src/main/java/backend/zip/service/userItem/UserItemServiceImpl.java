package backend.zip.service.userItem;

import backend.zip.domain.user.UserItem;
import backend.zip.domain.user.UserOption;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}