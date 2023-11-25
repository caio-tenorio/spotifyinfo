package com.spotifyinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyImage {
    private String url;
    private Integer height;
    private Integer width;
}
