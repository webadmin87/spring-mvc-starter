package ru.rzncenter.webcore.domains;

import java.util.Set;

/**
 * Интерфейс для объектов предоставляющих предпросмотр изображений
 */
public interface Previews {

    Set<? extends FileDomain> getPreviews();

    void setPreviews(Set<? extends FileDomain> previews);

    int previewWidth();

    int previewHeight();

}
