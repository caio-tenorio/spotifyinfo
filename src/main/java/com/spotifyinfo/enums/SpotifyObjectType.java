package com.spotifyinfo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<String, SpotifyObjectType> map = new HashMap<>();

    static {
        for (SpotifyObjectType modelObjectType : SpotifyObjectType.values()) {
            map.put(modelObjectType.type.toLowerCase(), modelObjectType);
        }
    }

    public final String type;

    SpotifyObjectType(final String type) {
        this.type = type;
    }

    @JsonCreator
    public static SpotifyObjectType keyOf(String type) {
        return map.get(type.toLowerCase());
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
