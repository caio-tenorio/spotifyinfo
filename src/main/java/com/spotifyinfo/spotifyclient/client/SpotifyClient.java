package com.spotifyinfo.spotifyclient.client;

import lombok.Data;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

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
//        this.redirectUri = config.getRedirectUri();
        this.redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/callback");

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();
    }

    public void authorizationCodeURI() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().build();
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI gerada: " + uri.toString());
        //TODO: Logger
    }
}
