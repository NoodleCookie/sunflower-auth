package sunflower.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.boot.actuate.health.HttpCodeStatusMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunflower.Authentication.CuzJwt;
import sunflower.dto.AuthenticationResponse;
import sunflower.dto.RegisterRequest;
import sunflower.entity.SunUser;
import sunflower.repository.SunUserRepository;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CuzJwt cuzJwt;

    private final SunUserRepository sunUserRepository;

    public AuthenticationServiceImpl(CuzJwt cuzJwt, SunUserRepository sunUserRepository) {
        this.cuzJwt = cuzJwt;
        this.sunUserRepository = sunUserRepository;
    }

    public AuthenticationResponse getJwt(String username) {
        return AuthenticationResponse.builder().code(HttpStatus.CREATED.value()).msg(cuzJwt.generatorJWT(username)).build();
    }

    private AuthenticationResponse auth(String username) {
        return null;
    }

    @Override
    public AuthenticationResponse verify(String token) {
        Claims claims = cuzJwt.parseJWT(token);
        if (cuzJwt.isExpireJwt(claims)) {
            throw new RuntimeException("jwr is expired");
        }
        return AuthenticationResponse.builder().code(200).msg(claims.toString()).build();
    }

    @SneakyThrows
    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        sunUserRepository.save(SunUser.builder()
                .id(UUID.randomUUID().toString())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .enable(true).build());

        return getJwt(registerRequest.getUsername());
    }
}
