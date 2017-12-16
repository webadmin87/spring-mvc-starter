package ru.rzncenter.webcore.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Компонент для работы с JSON
 */
@Component
public class JsonUtilsImpl implements JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilsImpl.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public <T> T jsonToObject(String json, TypeReference<T> ref) {
        if(json==null) {
            return null;
        }
        try {
            T value = MAPPER.readValue(json, ref);
            return value;
        } catch (Exception e) {
            LOGGER.error("Json read error", e);
            return null;
        }
    }

}
