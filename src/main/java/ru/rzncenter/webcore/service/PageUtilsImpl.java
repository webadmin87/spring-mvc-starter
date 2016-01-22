package ru.rzncenter.webcore.service;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * Сервис для работы с пагитнацией
 */
@Component
public class PageUtilsImpl implements PageUtils {

    @Override
    public <T> void pageToHeareds(HttpHeaders httpHeaders, Page<T> page) {

        httpHeaders.add("X-pagination-first", String.valueOf(page.isFirst()));
        httpHeaders.add("X-pagination-last", String.valueOf(page.isLast()));
        httpHeaders.add("X-pagination-number", String.valueOf(page.getNumber()));
        httpHeaders.add("X-pagination-number-of-elements", String.valueOf(page.getNumberOfElements()));
        httpHeaders.add("X-pagination-size", String.valueOf(page.getSize()));
        httpHeaders.add("X-pagination-total-elements", String.valueOf(page.getTotalElements()));
        httpHeaders.add("X-pagination-total-pages", String.valueOf(page.getTotalPages()));

    }
}
