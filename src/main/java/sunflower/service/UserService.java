package sunflower.service;


import org.springframework.web.multipart.MultipartFile;
import sunflower.entity.SunUser;

public interface UserService {
    void changeNickName(String nickName);

    void changeAvatarUrl(MultipartFile avatar);

    SunUser getUserInfo();
}
