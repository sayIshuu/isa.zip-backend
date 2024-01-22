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
        return userItemRepository.findByUser(userId);
    }

    public void saveUserItem(UserItem userItem) {
        userItemRepository.save(userItem);
    }

}
