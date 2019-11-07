package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public class DataValue {
    
    private String dataElement;
    private String value;

    public String getDataElement() {
        return dataElement;
    }

    public void setDataElement(String dataElement) {
        this.dataElement = dataElement;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataValue{" + "dataElement=" + dataElement + ", value=" + value + '}';
    }
    
    
    
}
