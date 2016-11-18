package ru.rzncenter.webcore.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

/**
 * Интерфес сервиса для работы с пагинацией
 */
public interface PageUtils {

    <T> void pageToHeaders(HttpHeaders httpHeaders, Page<T> page);

}
