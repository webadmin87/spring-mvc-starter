package ru.rzncenter.webcore.web;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by anton on 16.11.14.
 */
@Component("paginationResult")
@Scope("prototype")
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
