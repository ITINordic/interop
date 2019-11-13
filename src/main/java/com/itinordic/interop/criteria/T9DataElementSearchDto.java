package com.itinordic.interop.criteria;

/**
 *
 * @author Charles Chigoriwa
 */
public class T9DataElementSearchDto extends DefaultSearchDto {
    
    private Boolean noOptions;
    
    private Integer unreachableOptionCount;
    
    public Boolean getNoOptions() {
        return noOptions;
    }

    public void setNoOptions(Boolean noOptions) {
        this.noOptions = noOptions;
    }

    public Integer getUnreachableOptionCount() {
        return unreachableOptionCount;
    }

    public void setUnreachableOptionCount(Integer unreachableOptionCount) {
        this.unreachableOptionCount = unreachableOptionCount;
    }
    
    public boolean hasNoOptions(){
        return noOptions != null && noOptions;
    }
    
    

}
