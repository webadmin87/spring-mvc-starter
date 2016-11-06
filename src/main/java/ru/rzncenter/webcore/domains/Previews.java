package ru.rzncenter.webcore.domains;

import java.util.SortedSet;

/**
 * Интерфейс для объектов предоставляющих предпросмотр изображений
 */
public interface Previews {

    SortedSet<? extends FileDomain> getPreviews();

    void setPreviews(SortedSet<? extends FileDomain> previews);

    int previewWidth();

    int previewHeight();

}
