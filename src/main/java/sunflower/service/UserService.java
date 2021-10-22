package sunflower.service;


import org.springframework.web.multipart.MultipartFile;
import sunflower.entity.SunUser;

public interface UserService {
    SunUser changeNickName(String nickName);

    String changeAvatarUrl(MultipartFile avatar);

    SunUser getUserInfo();
}
