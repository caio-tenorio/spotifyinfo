package com.spotifyinfo.services.spotify;

import com.spotifyinfo.client.SpotifyClient;
import com.spotifyinfo.client.SpotifyClientConfig;
import com.spotifyinfo.domain.AccessTokenResponseDTO;
import com.spotifyinfo.domain.AuthorizationCodeUriResponseDTO;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Service
public class SpotifyAuthService {
    @Value("${spotify.client-id}")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;
    @Value("${spotify.redirect-uri}")
    private String redirectUri;

    @PostConstruct
    private void init() {
        if (StringUtils.isBlank(this.clientId)) {
            throw new RuntimeException("Client id is not present in configuration!");
        }

        if (StringUtils.isBlank(this.clientSecret)) {
            throw new RuntimeException("Client secret is not present in configuration!");
        }

        if (StringUtils.isBlank(this.redirectUri)) {
            throw new RuntimeException("Redirect Uri is not present in configuration!");
        }
    }

    public AuthorizationCodeUriResponseDTO getAuthorizationURI() {
        return getSpotifyClient().getAuthorizationCodeURI();
    }

    public AccessTokenResponseDTO getAuthorizationCode(String code) {
        return getSpotifyClient().getAuthorizationCode(code);
    }

    public AccessTokenResponseDTO getClientCredentials() {
        return getSpotifyClient().getClientCredentials();
    }

    private SpotifyClient getSpotifyClient() {
        SpotifyClientConfig config = generateConfig();
        return new SpotifyClient(config);
    }

    private SpotifyClientConfig generateConfig() {
        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);

        try {
            config.setRedirectUri(new URI(redirectUri));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating URI! Is not a valid format!");
        }
        return config;
    }
}
