package com.spotifyinfo.spotifyclient.client;

import lombok.Data;

import java.net.URI;

@Data
public class SpotifyClientConfig {
    private String clientId;
    private String clientSecret;
    private URI redirectUri;
}
