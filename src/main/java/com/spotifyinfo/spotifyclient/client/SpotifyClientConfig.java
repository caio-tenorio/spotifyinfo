package com.spotifyinfo.spotifyclient.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyClientConfig {
    private String clientId;
    private String clientSecret;
    private URI redirectUri;
    private String accessToken;
    private String refreshToken;
}
