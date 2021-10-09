package sunflower.service;

import sunflower.dto.AuthenticationResponse;
import sunflower.dto.AuthenticationRequest;

import java.util.Map;

public interface AuthenticationService {

    AuthenticationResponse verify(String token);

    AuthenticationResponse register(AuthenticationRequest authenticationRequest);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    Map<String, String> wxLogin(Map<String,String> loginRequest);
}
