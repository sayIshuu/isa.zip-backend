package backend.zip.service.userItem;

import backend.zip.domain.user.UserItem;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;

public interface UserItemService {
    public UserItem saveUserItem(Long userId, String address, String dong, AddUserItemOptionsRequest addUserItemOptionsRequest);
}
