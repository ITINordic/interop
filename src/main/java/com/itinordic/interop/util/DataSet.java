package com.itinordic.interop.util;

import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class DataSet {
    
    private String id;
    private String code;
    private String name;
    private List<DataSetElement> dataSetElements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataSetElement> getDataSetElements() {
        return dataSetElements;
    }

    public void setDataSetElements(List<DataSetElement> dataSetElements) {
        this.dataSetElements = dataSetElements;
    }

    @Override
    public String toString() {
        return "DataSet{" + "id=" + id + ", code=" + code + ", name=" + name + ", dataSetElements=" + dataSetElements + '}';
    }

  
    
    
    
}
