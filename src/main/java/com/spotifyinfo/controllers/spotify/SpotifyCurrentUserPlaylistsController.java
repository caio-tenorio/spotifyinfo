package com.spotifyinfo.controllers.spotify;

import com.spotifyinfo.domain.SpotifyPaging;
import com.spotifyinfo.domain.SpotifySimplifiedPlaylist;
import com.spotifyinfo.services.spotify.SpotifyPlaylistsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/spotify/me/playlists")
@AllArgsConstructor
public class SpotifyCurrentUserPlaylistsController {

    private final SpotifyPlaylistsService playlistsService;

    @GetMapping()
    public SpotifyPaging<SpotifySimplifiedPlaylist> getCurrentUserPlaylists(@RequestParam(name = "accessToken", required = true) String accessToken) {
        return playlistsService.getCurrentUserPlaylists(accessToken);
    }
}
