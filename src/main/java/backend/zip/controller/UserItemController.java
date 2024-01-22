package backend.zip.controller;

import backend.zip.domain.user.UserItem;
import backend.zip.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserItemController {

    private final UserItemService userItemService;

    @GetMapping("/items")
    public List<UserItem> getAllUserItems() {
        return userItemService.getAllUserItems();
    }

    @GetMapping("{userId}/items")
    public List<UserItem> getUserItemByUserId(@PathVariable Long userId) {
        return userItemService.getUserItemByUserId(userId);
    }
    @PostMapping("{userId}/items")
    public void saveUserItem(@RequestBody UserItem userItem) {
        userItemService.saveUserItem(userItem);
    }

}