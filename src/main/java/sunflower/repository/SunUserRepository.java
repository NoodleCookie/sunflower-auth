package sunflower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sunflower.entity.SunUser;

import java.util.Optional;

@Repository
public interface SunUserRepository extends JpaRepository<SunUser,String> {

    Optional<SunUser> findSunUserByUsername(String username);

    @Modifying
    @Query("update SunUser u set u.avatar_url=?1 where u.username=?2")
    void updateAvatarUrlByUsername(String avatar,String username);

    @Modifying
    @Query("update SunUser u set u.nickName=?1 where u.username=?2")
    String updateNicknamelByUsername(String nickname,String username);

}
