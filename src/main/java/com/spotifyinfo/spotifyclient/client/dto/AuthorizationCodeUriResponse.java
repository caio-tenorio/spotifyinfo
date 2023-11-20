package com.spotifyinfo.spotifyclient.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationCodeUriResponse {
    private URI uri;
}
