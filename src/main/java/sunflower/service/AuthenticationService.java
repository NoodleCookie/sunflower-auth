package sunflower.service;

import sunflower.dto.AuthenticationResponse;
import sunflower.dto.AuthenticationRequest;

public interface AuthenticationService {

    AuthenticationResponse verify(String token);

    AuthenticationResponse register(AuthenticationRequest authenticationRequest);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
