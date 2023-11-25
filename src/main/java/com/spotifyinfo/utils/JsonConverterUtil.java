package com.spotifyinfo.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonConverterUtil {
    public static String objectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to json");
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error converting json to object");
        }
    }

    public static <T> List<T> convertList(List<?> sourceList, Class<T> targetType) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType targetListType = objectMapper.getTypeFactory().constructCollectionType(List.class, targetType);

        try {
            return objectMapper.convertValue(sourceList, targetListType);
        } catch (Exception e) {
            throw new RuntimeException("Error converting list", e);
        }
    }
}
