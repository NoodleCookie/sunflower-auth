package sunflower.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sunflower.dto.AuthenticationResponse;
import sunflower.dto.RegisterRequest;
import sunflower.service.AuthenticationService;

import javax.validation.Valid;

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
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }
}
