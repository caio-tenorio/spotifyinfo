package com.spotifyinfo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true)
public class SpotifyUser {
    private SpotifyExternalUrl externalUrls;
    private SpotifyFollowers followers;
    private String href;
    private String id;
    private String type;
    private String uri;
    private String displayName;
}
