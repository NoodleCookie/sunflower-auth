package sunflower.service;

import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunflower.Authentication.CuzJwt;
import sunflower.dto.AuthenticationResponse;
import sunflower.dto.AuthenticationRequest;
import sunflower.entity.SunUser;
import sunflower.repository.SunUserRepository;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CuzJwt cuzJwt;

    private final SunUserRepository sunUserRepository;

    private final PasswordEncoder encoder;

    public AuthenticationServiceImpl(CuzJwt cuzJwt, SunUserRepository sunUserRepository, PasswordEncoder encoder) {
        this.cuzJwt = cuzJwt;
        this.sunUserRepository = sunUserRepository;
        this.encoder = encoder;
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
    public AuthenticationResponse register(AuthenticationRequest authenticationRequest) {
        sunUserRepository.save(SunUser.builder()
                .id(UUID.randomUUID().toString())
                .username(authenticationRequest.getUsername())
                .password(encoder.encode(authenticationRequest.getPassword()))
                .enable(true).build());

        return getJwt(authenticationRequest.getUsername());
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Optional<SunUser> sunUser = sunUserRepository.findSunUserByUsername(authenticationRequest.getUsername());
        SunUser foundedUser = sunUser.orElseThrow(() -> new RuntimeException("User not found"));
        if (!foundedUser.isEnable()) {
            throw new RuntimeException("user is disable");
        }
        if (!encoder.matches(authenticationRequest.getPassword(), foundedUser.getPassword())) {
            throw new RuntimeException("pass word not correct");
        }
        return AuthenticationResponse.builder().code(200).msg("login success").build();
    }
}
