package sunflower.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sunflower.entity.SunUser;
import sunflower.service.UserService;

import java.util.Map;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/avatar")
    public void changeAvatar(@RequestPart("file") MultipartFile file) {
        userService.changeAvatarUrl(file);
    }

    @GetMapping("/user")
    public SunUser get() {
        return userService.getUserInfo();
    }

    @PostMapping("/user/nickname")
    public void getNickname(Map<String, String> nickname) {
        userService.changeNickName(nickname.get("nickname"));
    }

}
