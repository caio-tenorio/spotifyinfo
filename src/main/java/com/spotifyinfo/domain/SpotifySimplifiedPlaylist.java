package com.spotifyinfo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spotifyinfo.enums.SpotifyObjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifySimplifiedPlaylist {
    private Boolean collaborative;
    private SpotifyExternalUrl externalUrls;
    private String href;
    private String id;
    private SpotifyImage[] images;
    private String name;
    private SpotifyUser owner;
    private Boolean publicAccess;
    private String snapshotId;
    private SpotifyTrackInformation tracks;
    private SpotifyObjectType type;
    private String uri;
}
