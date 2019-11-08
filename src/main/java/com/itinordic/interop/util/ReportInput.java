package com.itinordic.interop.util;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Charles Chigoriwa
 */
public class ReportInput implements Serializable{
    
    @NotNull(message="Select year")
    private Integer year;
    @NotNull(message="Select month")
    private Integer month;
    private Boolean sync;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }
    
    public String getYearMonth(){
        return year + ""+getFormattedMonth();
    }
    
    public String getFormattedMonth(){
        if(month<10){
            return "0"+month;
        }
        return ""+month;
    }
    
}
