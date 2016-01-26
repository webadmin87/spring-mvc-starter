package ru.rzncenter.webcore.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Компонент для работы с JSON
 */
@Component
public class JsonUtils {

    public <T> T jsonToObject(String json, TypeReference<T> ref) {

        if(json==null)
            return null;

        ObjectMapper mapper = new ObjectMapper();

        try {

            T value = mapper.readValue(json, ref);

            return value;

        } catch (Exception e) {

            return null;

        }


    }

}
