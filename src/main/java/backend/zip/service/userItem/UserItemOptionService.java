package backend.zip.service.userItem;

import backend.zip.domain.user.UserOption;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;

public interface UserItemOptionService {
    public UserOption saveUserItemOptions(AddUserItemOptionsRequest addUserItemOptionsRequest);
}
