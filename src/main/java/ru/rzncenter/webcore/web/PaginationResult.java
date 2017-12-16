package ru.rzncenter.webcore.web;

import java.util.List;

/**
 * Created by anton on 16.11.14.
 */
public class PaginationResult<T> {

    private List result;
    private Pagination pager;

    public PaginationResult(List<T> inpResult, Pagination inpPager) {
        result = inpResult;
        pager = inpPager;
    }

    public List<T> getResult() {
        return result;
    }

    public Pagination getPager() {
        return pager;
    }

}
