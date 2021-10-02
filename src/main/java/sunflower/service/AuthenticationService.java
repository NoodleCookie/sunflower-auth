package sunflower.service;

import sunflower.dto.AuthenticationResponse;
import sunflower.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse auth(String username);

    AuthenticationResponse verify(String token);

    AuthenticationResponse register(RegisterRequest registerRequest);
}
