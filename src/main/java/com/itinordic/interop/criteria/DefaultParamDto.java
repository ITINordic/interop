package com.itinordic.interop.criteria;

import com.itinordic.interop.util.GeneralUtility;
import java.util.Date;

/**
 *
 * @author Charles Chigoriwa
 */
public class DefaultParamDto {

    private String tab = "";
    private String action = "";
    private Long loadTime = new Date().getTime();
    private String page = "";
    private boolean deleted = false;
    private String src;

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
    
    public boolean hasSrc(){
        return !GeneralUtility.isEmpty(src);
    }
    
    public boolean srcEquals(String string){
        return hasSrc() && string.equalsIgnoreCase(src);
    }

    public Date getLoadTimeAsDate() {
        if (loadTime != null) {
            Date loadDateTime = new Date(loadTime);
            return loadDateTime;
        } else {
            return null;
        }
    }

    public boolean tabEquals(String tab) {
        return this.tab.trim().equalsIgnoreCase(tab.trim());
    }

    public boolean actionEquals(String action) {
        return this.action.trim().equalsIgnoreCase(action.trim());
    }

    public int getPageNumber() {
        int pageNumber = (page == null) ? 1 : GeneralUtility.parsePositiveInt(page);
        pageNumber = (pageNumber < 1) ? 1 : pageNumber;
        return pageNumber;
    }

}
