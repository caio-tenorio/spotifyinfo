package com.spotifyinfo.services.spotify;

import com.spotifyinfo.client.SpotifyClient;
import com.spotifyinfo.domain.AccessTokenResponseDTO;
import com.spotifyinfo.domain.AuthorizationCodeUriResponseDTO;
import com.spotifyinfo.domain.SpotifyAccessTokenResponse;
import com.spotifyinfo.domain.SpotifyAuthorizationCodeUriResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SpotifyAuthService {
    @Autowired
    private final SpotifyClient spotifyClient;

    public AuthorizationCodeUriResponseDTO getAuthorizationURI() {
        return convertSpotifyAuthorizationCodeUriResponseToDTO(this.spotifyClient.getAuthorizationCodeURI());
    }

    public AccessTokenResponseDTO getAuthorizationCode(String code) {
        return convertSpotifyAccessTokenResponseToDTO(this.spotifyClient.getAuthorizationCode(code));
    }

    public AccessTokenResponseDTO getClientCredentials() {
        return convertSpotifyAccessTokenResponseToDTO(this.spotifyClient.getClientCredentials());
    }

    private AccessTokenResponseDTO convertSpotifyAccessTokenResponseToDTO(SpotifyAccessTokenResponse spotifyAccessTokenResponse) {
        if (Objects.isNull(spotifyAccessTokenResponse)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error converting SpotifyAccessTokenResponse");
        }

        AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO();

        accessTokenResponseDTO.setAccessToken(spotifyAccessTokenResponse.getAccessToken());
        accessTokenResponseDTO.setRefreshToken(spotifyAccessTokenResponse.getRefreshToken());
        accessTokenResponseDTO.setState(spotifyAccessTokenResponse.getState());
        accessTokenResponseDTO.setScope(spotifyAccessTokenResponse.getScope());
        accessTokenResponseDTO.setError(spotifyAccessTokenResponse.getError());
        accessTokenResponseDTO.setTokenType(spotifyAccessTokenResponse.getTokenType());
        accessTokenResponseDTO.setExpiresIn(spotifyAccessTokenResponse.getExpiresIn());
        return accessTokenResponseDTO;
    }

    private AuthorizationCodeUriResponseDTO convertSpotifyAuthorizationCodeUriResponseToDTO(SpotifyAuthorizationCodeUriResponse spotifyAuthorizationCodeUriResponse) {
        AuthorizationCodeUriResponseDTO authorizationCodeUriResponseDTO = new AuthorizationCodeUriResponseDTO();
        authorizationCodeUriResponseDTO.setUri(spotifyAuthorizationCodeUriResponse.getUri());
        return authorizationCodeUriResponseDTO;
    }
}
