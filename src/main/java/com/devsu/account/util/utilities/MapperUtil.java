package com.devsu.account.util.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private MapperUtil() {
    }

    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entityClass) {
        return objectMapper.convertValue(map, entityClass);
    }

    public static MapperUtil createMapperUtil() {
        return new MapperUtil();
    }
}
