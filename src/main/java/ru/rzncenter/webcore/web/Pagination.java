package ru.rzncenter.webcore.web;

import org.springframework.http.HttpHeaders;

import javax.persistence.Query;

/**
 * Интерфейс для постраничной навигации
 */
public interface Pagination {

    Long getTotalCount();

    void setTotalCount(Long totalCount);

    Integer getPageSize();

    void setPageSize(Integer pageSize);

    Integer getTotalPages();

    Integer getPage();

    void setPage(Integer page);

    Integer getOffset();

    void applyLimits(Query q);

    void applyHeaders(HttpHeaders headers);

}
