package com.spotifyinfo.domain;

import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.miscellaneous.PlaylistTracksInformation;
import se.michaelthelin.spotify.model_objects.specification.ExternalUrl;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.User;

public record SimplifiedPlaylist(Boolean collaborative, ExternalUrl externalUrls, String href, String id,
                                 Image[] images, String name, User owner, Boolean publicAccess, String snapshotId,
                                 PlaylistTracksInformation tracks, ModelObjectType type, String uri) {
}
