package ru.rzncenter.webcore.domains;

import java.util.Map;
import java.util.SortedMap;

/**
 * Интерфейс для объектов предоставляющих предпросмотр изображений
 */
public interface Previews {

    Map<Integer, String> getPreviews();

    void setPreviews(SortedMap<Integer, String> previews);

    int previewWidth();

    int previewHeight();

}
