package ru.rzncenter.webcore.json;

import com.fasterxml.jackson.core.type.TypeReference;


public interface JsonUtils {

    <T> T jsonToObject(String json, TypeReference<T> ref);

}
