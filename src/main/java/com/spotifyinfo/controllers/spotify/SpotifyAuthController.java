package com.spotifyinfo.controllers.spotify;

import com.spotifyinfo.domain.AccessTokenResponseDTO;
import com.spotifyinfo.domain.AuthorizationCodeUriResponseDTO;
import com.spotifyinfo.services.spotify.SpotifyAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/v1/spotify/auth")
@AllArgsConstructor
public class SpotifyAuthController {
    private final SpotifyAuthService authService;

    @GetMapping("/authorization-uri")
    public AuthorizationCodeUriResponseDTO getAuthorizationURI() {
        return authService.getAuthorizationURI();
    };

    @GetMapping("/authorization-token")
    public AccessTokenResponseDTO getAuthorizationToken(@RequestParam String code) {
        return authService.getAuthorizationCode(code);
    };

    @GetMapping("/client-credentials")
    public AccessTokenResponseDTO getClientCredentials() {
        return authService.getClientCredentials();
    };
}
