package com.spotifyinfo.spotifyclient.client;

import com.spotifyinfo.spotifyclient.client.dto.AccessTokenResponse;
import com.spotifyinfo.spotifyclient.client.dto.AuthorizationCodeUriResponse;
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
    public AccessTokenResponse getClientCredentials() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            //TODO: logar tempo que expira
//            System.out.println("Expires in: " + clientCredentials.getExpiresIn());

            return AccessTokenResponse.builder()
                    .accessToken(clientCredentials.getAccessToken())
                    .tokenType(clientCredentials.getTokenType())
                    .expiresIn(clientCredentials.getExpiresIn())
                    .build();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            //TODO: logar erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public AccessTokenResponse getClientCredentialsAsync() {
//        try {
//            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
//            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();
//
//            clientCredentialsFuture.thenApply(clientCredentials -> {
//                spotifyApi.setAccessToken(clientCredentials.getAccessToken());
//                //TODO: Logar o tempo que expira o código clientCredentials.getExpiresIn()
//                return AccessTokenResponse.builder()
//                        .accessToken(clientCredentials.getAccessToken())
//                        .tokenType(clientCredentials.getTokenType())
//                        .expiresIn(clientCredentials.getExpiresIn())
//                        .build();
//                //TODO: Logar o tempo que expira o código clientCredentials.getExpiresIn()
//            }).exceptionally(e -> {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//                // TODO: Logar erro
//            });
//
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
//            // TODO: Logar exceções
//        }
//    }


    public AuthorizationCodeUriResponse getAuthorizationCodeURI() {
        try {
            AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();
            URI uri = authorizationCodeUriRequest.execute();

            if (uri == null) {
                throw new RuntimeException("The returned URI is null!");
            }

            return new AuthorizationCodeUriResponse(uri);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
            //        System.out.println("URI gerada: " + uri.toString());
            //TODO: Logger
        }
    }
}
