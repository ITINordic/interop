package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public class Pager {
    
    private Integer page;
    private Integer pageCount;
    private Integer total;
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Pager{" + "page=" + page + ", pageCount=" + pageCount + ", total=" + total + ", pageSize=" + pageSize + '}';
    }
    
    
    
}
