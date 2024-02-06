package backend.zip.service.userItem;

import backend.zip.domain.user.UserItem;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.dto.useritem.response.UserItemByDongResponse;
import backend.zip.dto.useritem.response.UserItemDongCountResponse;

import java.util.List;

public interface UserItemService {
    public UserItem saveUserItem(Long userId, String address, String dong, AddUserItemOptionsRequest addUserItemOptionsRequest);

    List<UserItemDongCountResponse> getUserItemDongCount(Long userId);

    List<UserItemByDongResponse> getUserItemSortedByDong();
}
