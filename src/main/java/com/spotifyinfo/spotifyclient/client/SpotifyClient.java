package com.spotifyinfo.spotifyclient.client;

import com.spotifyinfo.spotifyclient.domain.spotify.auth.AccessTokenResponseDTO;
import com.spotifyinfo.spotifyclient.domain.spotify.auth.AuthorizationCodeUriResponseDTO;
import lombok.Data;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;

@Data
public class SpotifyClient {
    private String clientId;
    private String clientSecret;
    private URI redirectUri;
    private SpotifyApi spotifyApi;
    private String accessToken;
    private String refreshToken;

    public SpotifyClient(SpotifyClientConfig config) {
        this.clientId = config.getClientId();
        this.clientSecret = config.getClientSecret();
        this.redirectUri = config.getRedirectUri();

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }
    public AccessTokenResponseDTO getClientCredentials() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            //TODO: logar tempo que expira
            return AccessTokenResponseDTO.builder()
                    .accessToken(clientCredentials.getAccessToken())
                    .tokenType(clientCredentials.getTokenType())
                    .expiresIn(clientCredentials.getExpiresIn())
                    .build();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            //TODO: logar erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AuthorizationCodeUriResponseDTO getAuthorizationCodeURI() {
        try {
            AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();
            URI uri = authorizationCodeUriRequest.execute();

            if (uri == null) {
                throw new RuntimeException("The returned URI is null!");
            }

            return new AuthorizationCodeUriResponseDTO(uri);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
            //        System.out.println("URI gerada: " + uri.toString());
            //TODO: Logger
        }
    }
}
