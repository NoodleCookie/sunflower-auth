package sunflower.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sunflower.configuration.CuzJwt;
import sunflower.configuration.WxConfiguration;
import sunflower.dto.AuthCodeResponse;
import sunflower.dto.AuthenticationResponse;
import sunflower.dto.AuthenticationRequest;
import sunflower.entity.SunUser;
import sunflower.repository.SunUserRepository;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CuzJwt cuzJwt;

    private final SunUserRepository sunUserRepository;

    private final PasswordEncoder encoder;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final WxConfiguration wxConfiguration;

    public AuthenticationServiceImpl(CuzJwt cuzJwt, SunUserRepository sunUserRepository, PasswordEncoder encoder, WxConfiguration wxConfiguration) {
        this.cuzJwt = cuzJwt;
        this.sunUserRepository = sunUserRepository;
        this.encoder = encoder;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.wxConfiguration = wxConfiguration;
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
        return AuthenticationResponse.builder().code(200).msg(claims.toString()).principle(claims.getSubject()).build();
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
        return AuthenticationResponse.builder().code(HttpStatus.OK.value()).msg(cuzJwt.generatorJWT(authenticationRequest.getUsername())).build();
    }

    @Override
    public Map<String, String> wxLogin(Map<String, String> loginRequest) {
        String code = loginRequest.get("code");

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("wx code cannot be empty");
        }

        AuthCodeResponse body = authCodeSession(code);

        String jwtToken = cuzJwt.generatorJWT(body.getOpenId());

        HashMap<String, String> result = new HashMap<>();
        result.put("jwt", jwtToken);
        result.put("openid", body.getOpenId());
        result.put("unionid", body.getUnionId());
        return result;
    }

    @SneakyThrows
    private AuthCodeResponse authCodeSession(String code) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", wxConfiguration.getAppid());
        queryParams.add("secret", wxConfiguration.getSecret());
        queryParams.add("js_code", code);
        queryParams.add("grant_type", "authorization_code");
        URI uri = UriComponentsBuilder.fromHttpUrl(wxConfiguration.getUrl() + "/sns/jscode2session")
                .queryParams(queryParams)
                .build()
                .toUri();

        ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
        AuthCodeResponse authCodeResponse = this.objectMapper.readValue(result.getBody(), AuthCodeResponse.class);
        if (authCodeResponse.getErrcode() != 0) {
            throw new RuntimeException("wx error");
        }
        return authCodeResponse;
    }
}
