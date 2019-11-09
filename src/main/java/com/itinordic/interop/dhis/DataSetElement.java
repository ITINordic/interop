package com.itinordic.interop.dhis;

/**
 *
 * @author Charles Chigoriwa
 */
public class DataSetElement {
    
    private DataElement dataElement;

    public DataElement getDataElement() {
        return dataElement;
    }

    public void setDataElement(DataElement dataElement) {
        this.dataElement = dataElement;
    }

    @Override
    public String toString() {
        return "DataSetDataElement{" + "dataElement=" + dataElement + '}';
    }
    
    
    
}
