package com.spotifyinfo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
}
