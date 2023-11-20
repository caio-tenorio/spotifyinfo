package com.spotifyinfo.spotifyclient.rest.auth;

import com.spotifyinfo.spotifyclient.client.dto.AccessTokenResponse;
import com.spotifyinfo.spotifyclient.client.dto.AuthorizationCodeUriResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/authentication-url")
    public AuthorizationCodeUriResponse getAuthorizationURI(@RequestParam String clientId, @RequestParam String clientSecret) {
        return authService.getAuthorizationURI(clientId, clientSecret);
    };

    @GetMapping("/client-credentials")
    public AccessTokenResponse getClientCredentials(@RequestParam String clientId, @RequestParam String clientSecret) {
        return authService.getClientCredentials(clientId, clientSecret);
    };
}
