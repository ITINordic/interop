package com.itinordic.interop.util;

import com.itinordic.interop.dhis.DataElement;

/**
 *
 * @author Charles Chigoriwa
 */
public class ProgramStageDataElement {
    
    private DataElement dataElement;

    public DataElement getDataElement() {
        return dataElement;
    }

    public void setDataElement(DataElement dataElement) {
        this.dataElement = dataElement;
    }

    @Override
    public String toString() {
        return "ProgramStageDataElement{" + "dataElement=" + dataElement + '}';
    }
    
    
}
