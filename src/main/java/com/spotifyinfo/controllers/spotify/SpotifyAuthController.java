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
    public AuthorizationCodeUriResponseDTO getAuthorizationURI(@RequestParam(required = false) String clientId,
                                                               @RequestParam(required = false) String clientSecret,
                                                               @RequestParam(required = false) String redirectUri) {
        return authService.getAuthorizationURI(clientId, clientSecret, redirectUri);
    };

    @GetMapping("/authorization-token")
    public AccessTokenResponseDTO getAuthorizationToken(@RequestParam String code,
                                                        @RequestParam(required = false) String clientId,
                                                        @RequestParam(required = false) String clientSecret,
                                                        @RequestParam(required = false) String redirectUri) {
        return authService.getAuthorizationCode(code, clientId, clientSecret, redirectUri);
    };

    @GetMapping("/client-credentials")
    public AccessTokenResponseDTO getClientCredentials(@RequestParam String clientId, @RequestParam String clientSecret) {
        return authService.getClientCredentials(clientId, clientSecret);
    };
}
