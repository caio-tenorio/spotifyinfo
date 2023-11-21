package com.spotifyinfo.spotifyclient.controllers;

import com.spotifyinfo.spotifyclient.domain.spotify.auth.AccessTokenResponseDTO;
import com.spotifyinfo.spotifyclient.domain.spotify.auth.AuthorizationCodeUriResponseDTO;
import com.spotifyinfo.spotifyclient.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/authentication-url")
    public AuthorizationCodeUriResponseDTO getAuthorizationURI(@RequestParam String clientId, @RequestParam String clientSecret) {
        return authService.getAuthorizationURI(clientId, clientSecret);
    };

    @GetMapping("/client-credentials")
    public AccessTokenResponseDTO getClientCredentials(@RequestParam String clientId, @RequestParam String clientSecret) {
        return authService.getClientCredentials(clientId, clientSecret);
    };
}
