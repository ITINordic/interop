package com.itinordic.interop.criteria;

/**
 *
 * @author Charles Chigoriwa
 */
public class DefaultSearchDto extends DefaultParamDto {

    private String q;
    private boolean directSearch = false;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getSearchQueryString() {
        return q;
    }

    public boolean isDirectSearch() {
        return directSearch;
    }

    public void setDirectSearch(boolean directSearch) {
        this.directSearch = directSearch;
    }

}
