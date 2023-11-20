package com.spotifyinfo.spotifyclient.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponse {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
}
