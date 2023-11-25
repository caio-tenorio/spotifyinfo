package com.spotifyinfo.enums;

import lombok.Getter;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SpotifyObjectType {
    ALBUM("album"),
    ARTIST("artist"),
    AUDIO_FEATURES("audio_features"),
    EPISODE("episode"),
    GENRE("genre"),
    PLAYLIST("playlist"),
    SHOW("show"),
    TRACK("track"),
    USER("user");

    private static final Map<String, ModelObjectType> map = new HashMap<>();

    static {
        for (ModelObjectType modelObjectType : ModelObjectType.values()) {
            map.put(modelObjectType.type, modelObjectType);
        }
    }

    public final String type;

    SpotifyObjectType(final String type) {
        this.type = type;
    }

    public static ModelObjectType keyOf(String type) {
        return map.get(type);
    }
}
