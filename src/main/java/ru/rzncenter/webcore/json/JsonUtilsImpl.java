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

    private static final Logger logger = LoggerFactory.getLogger(JsonUtilsImpl.class);

    @Override
    public <T> T jsonToObject(String json, TypeReference<T> ref) {
        if(json==null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            T value = mapper.readValue(json, ref);
            return value;
        } catch (Exception e) {
            logger.error("Json read error", e);
            return null;
        }
    }

}
