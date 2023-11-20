package com.spotifyinfo.spotifyclient.rest.auth;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/authentication-url")
    public AuthorizationCodeUriResponse getAuthorizationURI(@PathVariable String clientId, String clientSecret) {
        return authService.getAuthorizationURI(clientId, clientSecret);
    };
}
