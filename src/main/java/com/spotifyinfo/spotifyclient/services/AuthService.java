package com.spotifyinfo.spotifyclient.services;

import com.spotifyinfo.spotifyclient.domain.spotify.auth.AccessTokenResponseDTO;
import com.spotifyinfo.spotifyclient.domain.spotify.auth.AuthorizationCodeUriResponseDTO;
import com.spotifyinfo.spotifyclient.client.SpotifyClient;
import com.spotifyinfo.spotifyclient.client.SpotifyClientConfig;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService {

    public AuthorizationCodeUriResponseDTO getAuthorizationURI(String clientId, String clientSecret) {
        validateRequest(clientId, clientSecret);

        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);

        SpotifyClient spotifyClient = new SpotifyClient(config);
        return spotifyClient.getAuthorizationCodeURI();
    }

    public AccessTokenResponseDTO getClientCredentials(String clientId, String clientSecret) {
        validateRequest(clientId, clientSecret);
        SpotifyClient spotifyClient = createClientInstance(clientId, clientSecret);
        return spotifyClient.getClientCredentials();
    }

    private void validateRequest(String clientId, String clientSecret) {
        if (StringUtils.isBlank(clientId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing clientId!");
        }

        if (StringUtils.isBlank(clientSecret)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing clientSecret!");
        }
    }

    private SpotifyClient createClientInstance(String clientId, String clientSecret) {
        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);

        return new SpotifyClient(config);
    }

    private SpotifyClient createClientInstance(String clientId, String clientSecret, String accessToken,
                                               String refreshToken) {
        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);
        config.setAccessToken(accessToken);
        config.setRefreshToken(refreshToken);

        return new SpotifyClient(config);
    }
}
