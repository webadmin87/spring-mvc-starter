package ru.rzncenter.webcore.web;

import org.springframework.http.HttpHeaders;

import javax.persistence.Query;

/**
 * Интерфейс для постраничной навигации
 */
public interface Pagination {

    long getTotalCount();

    void setTotalCount(long totalCount);

    int getPageSize();

    void setPageSize(int pageSize);

    int getTotalPages();

    int getPage();

    void setPage(int page);

    int getOffset();

    void applyLimits(Query q);

    void applyHeaders(HttpHeaders headers);

}
