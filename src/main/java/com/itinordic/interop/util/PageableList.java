package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public abstract class PageableList {
    
    protected Pager pager;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
