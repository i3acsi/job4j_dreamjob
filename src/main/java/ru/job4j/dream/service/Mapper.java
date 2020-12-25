package ru.job4j.dream.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.Store;

public class Mapper {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(Store.class);

    public static String CityToJson(City city) {
        try {
            return OBJECT_MAPPER.writeValueAsString(city);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return "";
        }
    }
}


