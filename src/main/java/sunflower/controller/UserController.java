package sunflower.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sunflower.entity.SunUser;
import sunflower.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/avatar")
    public String changeAvatar(@RequestPart("file") MultipartFile file) {
        return userService.changeAvatarUrl(file);
    }

    @GetMapping("/user")
    public SunUser get() {
        return userService.getUserInfo();
    }

}
