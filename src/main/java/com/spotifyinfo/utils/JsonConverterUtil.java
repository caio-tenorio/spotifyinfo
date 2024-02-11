package com.spotifyinfo.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

public class JsonConverterUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);;

    public static String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to json");
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error converting json to object", e);
        }
    }

    public static <T> List<T> convertList(List<?> sourceList, Class<T> targetType) {
        JavaType targetListType = objectMapper.getTypeFactory().constructCollectionType(List.class, targetType);

        try {
            return objectMapper.convertValue(sourceList, targetListType);
        } catch (Exception e) {
            throw new RuntimeException("Error converting list", e);
        }
    }

    public static <T> List<T> convertList(String list, Class<T> target) {
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, target);
            return objectMapper.readValue(list, listType);
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to List<SpotifySimplifiedPlaylist>", e);
        }
    }
}
