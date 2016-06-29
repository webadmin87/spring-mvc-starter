package ru.rzncenter.webcore.utils;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Содержит методы для работы с коллекциями
 */
@Component
public class CollectionUtilsImpl implements CollectionUtils {

    @Override
    public String join(List<String> list, String symbol) {
        StringBuilder builder = new StringBuilder();
        for(int i =0; i<list.size(); i++) {
            if(i>0) {
                builder.append(symbol);
            }
            builder.append(list.get(i));
        }
        return builder.toString();
    }

}
