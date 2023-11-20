package com.spotifyinfo.spotifyclient.rest.auth;

import com.spotifyinfo.spotifyclient.client.SpotifyClient;
import com.spotifyinfo.spotifyclient.client.SpotifyClientConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    public AuthorizationCodeUriResponse getAuthorizationURI(String clientId, String clientSecret) {
        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);

        SpotifyClient spotifyClient = new SpotifyClient(config);
        return spotifyClient.getAuthorizationCodeURI();
    }
}
