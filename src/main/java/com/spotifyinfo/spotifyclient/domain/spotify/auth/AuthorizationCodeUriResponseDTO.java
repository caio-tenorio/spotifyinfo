package com.spotifyinfo.spotifyclient.domain.spotify.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationCodeUriResponseDTO {
    private URI uri;
}
