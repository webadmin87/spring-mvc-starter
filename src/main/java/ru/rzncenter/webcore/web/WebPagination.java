package ru.rzncenter.webcore.web;

import org.springframework.http.HttpHeaders;
import javax.persistence.Query;

/**
 * Компонент для организации постраничной навигации
 */
public class WebPagination implements Pagination {

    private long totalCount;
    private int page = 1;
    private int pageSize = 10;

    public WebPagination(long inpTotalCount, int inpPage) {
        totalCount = inpTotalCount;
        page = inpPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return (int) Math.ceil(totalCount*1.0/pageSize);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return (page-1)*pageSize;
    }

    public void applyLimits(Query q) {
        q.setFirstResult(getOffset());
        q.setMaxResults(getPageSize());
    }

    public void applyHeaders(HttpHeaders headers) {
        headers.add("x-pagination-total-count", String.valueOf(getTotalCount()));
        headers.add("x-pagination-page-count", String.valueOf(getTotalPages()));
        headers.add("x-pagination-per-page", String.valueOf(getPageSize()));
    }

}
