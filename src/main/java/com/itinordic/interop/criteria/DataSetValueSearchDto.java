package com.itinordic.interop.criteria;

import com.itinordic.interop.util.GeneralUtility;

/**
 *
 * @author Charles Chigoriwa
 */
public class DataSetValueSearchDto extends DefaultSearchDto {

    private String eventPeriod;

    public String getEventPeriod() {
        return eventPeriod;
    }

    public void setEventPeriod(String eventPeriod) {
        this.eventPeriod = eventPeriod;
    }
    
    public boolean hasEventPeriod(){
        return !GeneralUtility.isEmpty(eventPeriod);
    }

}
