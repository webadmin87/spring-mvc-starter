package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

/**
 * Интерфес сервиса для работы с пагинацией
 */
public interface PageUtils {

    <T> void pageToHeareds(HttpHeaders httpHeaders, Page<T> page);

}
