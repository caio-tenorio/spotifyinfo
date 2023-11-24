package com.spotifyinfo.services.spotify;

import com.spotifyinfo.client.SpotifyClient;
import com.spotifyinfo.client.SpotifyClientConfig;
import com.spotifyinfo.domain.AccessTokenResponseDTO;
import com.spotifyinfo.domain.AuthorizationCodeUriResponseDTO;
import com.spotifyinfo.enums.AttrType;
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
    @Value("${spotify.redirect-uri}")
    private String redirectUri;
    @Value("${spotify.client-secret}")
    private String clientSecret;

    public AuthorizationCodeUriResponseDTO getAuthorizationURI(String clientId, String clientSecret, String redirectUri) {
        SpotifyClientConfig config = generateConfig(clientId, clientSecret, redirectUri);
        SpotifyClient spotifyClient = new SpotifyClient(config);
        return spotifyClient.getAuthorizationCodeURI();
    }

    public AccessTokenResponseDTO getAuthorizationCode(String code, String clientId, String clientSecret, String redirectUri) {
        SpotifyClientConfig spotifyClientConfig = generateConfig(clientId, clientSecret, redirectUri);

        SpotifyClient spotifyClient = new SpotifyClient(spotifyClientConfig);
        return spotifyClient.getAuthorizationCode(code);
    }

    public AccessTokenResponseDTO getClientCredentials(String clientId, String clientSecret) {
        SpotifyClient spotifyClient = createClientInstance(clientId, clientSecret);
        return spotifyClient.getClientCredentials();
    }

    private SpotifyClient createClientInstance(String clientId, String clientSecret) {
        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);

        return new SpotifyClient(config);
    }

    private SpotifyClientConfig generateConfig(String clientId, String clientSecret, String redirectUri) {
        SpotifyClientConfig config = new SpotifyClientConfig();
        config.setClientId(getValidValue(clientId, AttrType.CLIENT_ID));
        config.setClientSecret(getValidValue(clientSecret, AttrType.CLIENT_SECRET));

        try {
            config.setRedirectUri(new URI(getValidValue(redirectUri, AttrType.REDIRECT_URI)));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating URI! Is not a valid format!");
        }
        return config;
    }

    private String getValidValue(String attr, AttrType type) {
        if (type == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Type can't be null!");
        }

        switch (type) {
            case CLIENT_ID -> {
                return StringUtils.isNotBlank(attr) ? attr : this.clientId;
            }
            case CLIENT_SECRET -> {
                return StringUtils.isNotBlank(attr) ? attr : this.clientSecret;
            }
            case REDIRECT_URI -> {
                return StringUtils.isNotBlank(attr) ? attr : this.redirectUri;
            }
            default -> {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could no parse attribute type!");
            }
        }
    }
}
