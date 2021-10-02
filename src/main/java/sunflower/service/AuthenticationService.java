package sunflower.service;

import sunflower.dto.AuthenticationResponse;
import sunflower.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse verify(String token);

    AuthenticationResponse register(RegisterRequest registerRequest);
}
