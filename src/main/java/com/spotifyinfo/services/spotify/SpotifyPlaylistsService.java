package com.spotifyinfo.services.spotify;

import com.spotifyinfo.client.SpotifyClient;
import com.spotifyinfo.client.SpotifyClientConfig;
import com.spotifyinfo.domain.SpotifyPaging;
import com.spotifyinfo.domain.SpotifySimplifiedPlaylist;
import com.spotifyinfo.utils.JsonConverterUtil;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;

@Service
public class SpotifyPlaylistsService {

    public Paging<PlaylistSimplified> getCurrentUserPlaylists(String accessToken, String refreshToken) {
        SpotifyClient spotifyClient = new SpotifyClient(getConfig(accessToken, refreshToken));
        Paging<PlaylistSimplified> playlistSimplifiedPaging = spotifyClient.getListOfCurrentUsersPlaylists();
        SpotifyPaging<SpotifySimplifiedPlaylist> spotifySimplifiedPlaylistSpotifyPaging = convertToSimplifiedPlaylist(playlistSimplifiedPaging);
        return playlistSimplifiedPaging;
    }

    private SpotifyPaging<SpotifySimplifiedPlaylist> convertToSimplifiedPlaylist(Paging<PlaylistSimplified> playlistSimplifiedPaging) {
        List<SpotifySimplifiedPlaylist> playlists = JsonConverterUtil.convertList(List.of(playlistSimplifiedPaging.getItems()), SpotifySimplifiedPlaylist.class);

        return convertToSpotifyPaging(playlistSimplifiedPaging, playlists.toArray(new SpotifySimplifiedPlaylist[]{}));
    }

    private SpotifyPaging<SpotifySimplifiedPlaylist> convertToSpotifyPaging(Paging<PlaylistSimplified> playlistSimplifiedPaging, SpotifySimplifiedPlaylist[] spotifyPlaylists) {
        SpotifyPaging<SpotifySimplifiedPlaylist> spotifySimplifiedPlaylistSpotifyPaging = new SpotifyPaging<>();
        spotifySimplifiedPlaylistSpotifyPaging.setNext(playlistSimplifiedPaging.getNext());
        spotifySimplifiedPlaylistSpotifyPaging.setOffset(playlistSimplifiedPaging.getOffset());
        spotifySimplifiedPlaylistSpotifyPaging.setLimit(playlistSimplifiedPaging.getLimit());
        spotifySimplifiedPlaylistSpotifyPaging.setPrevious(playlistSimplifiedPaging.getPrevious());
        spotifySimplifiedPlaylistSpotifyPaging.setHref(playlistSimplifiedPaging.getHref());
        spotifySimplifiedPlaylistSpotifyPaging.setTotal(playlistSimplifiedPaging.getTotal());
        spotifySimplifiedPlaylistSpotifyPaging.setItems(spotifyPlaylists);
        return spotifySimplifiedPlaylistSpotifyPaging;
    }

    private SpotifyClientConfig getConfig(String accessToken, String refreshToken) {
        SpotifyClientConfig spotifyClientConfig = new SpotifyClientConfig();
        spotifyClientConfig.setAccessToken(accessToken);
        spotifyClientConfig.setRefreshToken(refreshToken);
        return spotifyClientConfig;
    }
}
