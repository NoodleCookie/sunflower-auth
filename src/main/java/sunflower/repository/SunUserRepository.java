package sunflower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sunflower.entity.SunUser;

import java.util.Optional;

@Repository
public interface SunUserRepository extends JpaRepository<SunUser,String> {

    Optional<SunUser> findSunUserByUsername(String username);
}
