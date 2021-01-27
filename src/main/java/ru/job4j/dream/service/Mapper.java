package ru.job4j.dream.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.store.Store;

public class Mapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Store.class);

    public static String objToJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    public static Object jsonToObject(String json, Class cl) {
        try {
            return OBJECT_MAPPER.readValue(json, cl);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("can't parse the json");
        }
    }
}


