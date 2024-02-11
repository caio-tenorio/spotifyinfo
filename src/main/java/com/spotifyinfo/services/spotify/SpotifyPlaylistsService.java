package com.spotifyinfo.services.spotify;

import com.spotifyinfo.client.SpotifyClient;
import com.spotifyinfo.domain.SpotifyPaging;
import com.spotifyinfo.domain.SpotifySimplifiedPlaylist;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpotifyPlaylistsService {
    @Autowired
    private final SpotifyClient spotifyClient;
    public SpotifyPaging<SpotifySimplifiedPlaylist> getCurrentUserPlaylists(String accessToken) {
        return this.spotifyClient.getListOfCurrentUsersPlaylists(accessToken);
    }
}
