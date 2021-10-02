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
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public AuthenticationResponse authentication(@RequestBody String authentication) {
        if (authentication.equals("authentication")) {
            return AuthenticationResponse.builder().code(1).msg("auth success").build();
        }
        throw new RuntimeException();
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }
}
