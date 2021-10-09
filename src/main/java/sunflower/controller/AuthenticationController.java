package sunflower.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sunflower.dto.AuthenticationResponse;
import sunflower.dto.AuthenticationRequest;
import sunflower.service.AuthenticationService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth")
    public AuthenticationResponse authentication(@RequestBody String authentication) {
        return authenticationService.verify(authentication);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.register(authenticationRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/wx-auth")
    public Map<String, String> wxLogin(@RequestBody Map<String, String> request) {
        return authenticationService.wxLogin(request);
    }
}
