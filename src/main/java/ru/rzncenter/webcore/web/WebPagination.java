package ru.rzncenter.webcore.web;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.persistence.Query;

/**
 * Компонент для организации постраничной навигации
 */
@Component("pagination")
@Scope("prototype")
public class WebPagination implements Pagination {

    Long totalCount;

    Integer page = 1;

    Integer pageSize = 10;

    public WebPagination(Long inpTotalCount, Integer inpPage) {

        totalCount = inpTotalCount;

        page = inpPage;

    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {

        Double pages = Math.ceil(totalCount*1.0/pageSize);

        return pages.intValue();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getOffset() {

        return (page-1)*pageSize;

    }

    public void applyLimits(Query q) {

        q.setFirstResult(getOffset());
        q.setMaxResults(getPageSize());

    }

    public void applyHeaders(HttpHeaders headers) {

        headers.add("x-pagination-total-count", getTotalCount().toString());
        headers.add("x-pagination-page-count", getTotalPages().toString());
        headers.add("x-pagination-per-page", getPageSize().toString());

    }

}
