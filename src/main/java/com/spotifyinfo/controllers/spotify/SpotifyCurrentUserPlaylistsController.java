package com.spotifyinfo.controllers.spotify;

import com.spotifyinfo.services.spotify.SpotifyPlaylistsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

@RestController
@RequestMapping("/v1/spotify/me/playlists")
@AllArgsConstructor
public class SpotifyCurrentUserPlaylistsController {

    private final SpotifyPlaylistsService playlistsService;

    @GetMapping()
    public Paging<PlaylistSimplified> getCurrentUserPlaylists(@RequestParam String accessToken, @RequestParam String refreshToken) {
        return playlistsService.getCurrentUserPlaylists(accessToken, refreshToken);
    }
}
