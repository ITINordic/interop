package com.itinordic.interop.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class Select2ObjectBag {

    private List<Select2Object> results = new ArrayList<>();
    private Select2Pagination pagination = new Select2Pagination();

    public List<Select2Object> getResults() {
        return results;
    }

    public void setResults(List<Select2Object> results) {
        this.results = results;
    }

    public Select2Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Select2Pagination pagination) {
        this.pagination = pagination;
    }

    public Select2ObjectBag add(Select2Object select2Object) {
        this.results.add(select2Object);
        return this;
    }

    public Select2ObjectBag setMore(boolean more) {
        this.pagination.setMore(more);
        return this;
    }

}
