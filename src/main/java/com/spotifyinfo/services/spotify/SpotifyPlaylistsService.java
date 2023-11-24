package com.spotifyinfo.services.spotify;

import com.spotifyinfo.client.SpotifyClient;
import com.spotifyinfo.client.SpotifyClientConfig;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

@Service
public class SpotifyPlaylistsService {

    public Paging<PlaylistSimplified> getCurrentUserPlaylists(String accessToken, String refreshToken) {
        SpotifyClient spotifyClient = new SpotifyClient(getConfig(accessToken, refreshToken));
        return spotifyClient.getListOfCurrentUsersPlaylists();
    }

//    private convertToPageSimplifiedPlaylist(Paging<PlaylistSimplified> pagingPlaylists) {
//        Pageable pageable = PageRequest.of(pagingPlaylists.getLimit(), )
//        Page page =
//    }

    private SpotifyClientConfig getConfig(String accessToken, String refreshToken) {
        SpotifyClientConfig spotifyClientConfig = new SpotifyClientConfig();
        spotifyClientConfig.setAccessToken(accessToken);
        spotifyClientConfig.setRefreshToken(refreshToken);
        return spotifyClientConfig;
    }
}
