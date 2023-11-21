package com.spotifyinfo.spotifyclient.domain.spotify.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponseDTO {
    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
}
