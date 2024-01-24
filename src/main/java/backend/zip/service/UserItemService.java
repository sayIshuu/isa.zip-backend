package backend.zip.service;

import backend.zip.domain.user.UserItem;
import backend.zip.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserItemRepository userItemRepository;

    public List<UserItem> getAllUserItems() {
        return userItemRepository.findAll();
    }

    public List<UserItem> getUserItemByUserId(Long userId) {
        return userItemRepository.findByUserId(userId);
    }

    public void saveUserItem(UserItem userItem) {
        userItemRepository.save(userItem);
    }

    //특정 UserItem을 특정 주소로 업데이트하기  -> 임시로 작성 이런 느낌으로 작성해주셔도 좋을 것 같습니다!
    //혹은 Long userItemId가 아닌 UserItem useritem을 인자로 받아서 여기서 id추출해서 업데이트
    //시켜도 되는데 그렇게 하시려면 리포지토리에서 JPQL로 작성한 쿼리문을 수정해주세요!!!!
    public void updateUserItemAddress(Long userItemId, String newAddress) {
        userItemRepository.updateAddressByUserItemId(userItemId, newAddress);
    }

}
