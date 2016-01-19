package ru.rzncenter.webcore.web;

import org.springframework.http.HttpHeaders;

import javax.persistence.Query;

/**
 * Интерфейс для постраничной навигации
 */
public interface Pagination {

    public Long getTotalCount();

    public void setTotalCount(Long totalCount);

    public Integer getPageSize();

    public void setPageSize(Integer pageSize);

    public Integer getTotalPages();

    public Integer getPage();

    public void setPage(Integer page);

    public Integer getOffset();

    public void applyLimits(Query q);

    public void applyHeaders(HttpHeaders headers);

}
