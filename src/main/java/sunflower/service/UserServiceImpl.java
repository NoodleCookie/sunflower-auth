package sunflower.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sunflower.configuration.OssConfiguration;
import sunflower.configuration.UserContext;
import sunflower.entity.SunUser;
import sunflower.repository.SunUserRepository;

import java.io.*;

@Service
public class UserServiceImpl implements UserService {

    private OssConfiguration ossConfiguration;

    private SunUserRepository sunUserRepository;

    public UserServiceImpl(OssConfiguration ossConfiguration, SunUserRepository sunUserRepository) {
        this.ossConfiguration = ossConfiguration;
        this.sunUserRepository = sunUserRepository;
    }

    @Override
    public String changeNickName(String nickName) {
        return sunUserRepository.updateNicknamelByUsername(nickName,UserContext.getUser());
    }

    @SneakyThrows
    @Override
    @Transactional
    public String changeAvatarUrl(MultipartFile avatar) {
        String pathname = UserContext.getUser() + "-" + avatar.getOriginalFilename();
        String url = null;

        try (FileOutputStream fileOutputStream = new FileOutputStream(pathname);
             InputStream inputStream = avatar.getInputStream()) {
            byte[] flush = new byte[1024];
            int len;
            while ((len = inputStream.read(flush)) != -1) {
                fileOutputStream.write(flush, 0, len);
            }
            fileOutputStream.flush();
        }

        OSS ossClient = null;
        try {
            url = "http://thoughtworks-sunflower.oss-cn-beijing.aliyuncs.com/" + ossConfiguration.getDirectory() + "/" + pathname;
            sunUserRepository.updateAvatarUrlByUsername(url, UserContext.getUser());

            ossClient = new OSSClientBuilder().build(ossConfiguration.getEndpoint(), ossConfiguration.getAccessKeyId(), ossConfiguration.getAccessKeySecret());

            ossClient.putObject(ossConfiguration.getBucketName(), ossConfiguration.getDirectory() + "/" + pathname, new File(pathname));

            ossClient.shutdown();

            new File(pathname).delete();

            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    public SunUser getUserInfo() {
        return sunUserRepository.findSunUserByUsername(UserContext.getUser()).orElseThrow(() -> new RuntimeException("no such user"));
    }

}
