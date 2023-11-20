package com.spotifyinfo.spotifyclient.client;

import com.spotifyinfo.spotifyclient.rest.auth.AuthorizationCodeUriResponse;
import lombok.Data;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

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
//        this.redirectUri = config.getRedirectUri();
        this.redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback");

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }
    public void clientCredentials_Sync() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            //TODO: logar tempo que expira
//            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            //TODO: logar erro
//            System.out.println("Error: " + e.getMessage());
        }
    }

    public void clientCredentials_Async() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            clientCredentialsFuture.thenAccept(clientCredentials -> {
                spotifyApi.setAccessToken(clientCredentials.getAccessToken());

                //TODO: Logar o tempo que expira o código clientCredentials.getExpiresIn()
            }).exceptionally(e -> {
                // TODO: Logar erro
                return null;
            });

        } catch (Exception e) {
            // TODO: Logar exceções
        }
    }


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
