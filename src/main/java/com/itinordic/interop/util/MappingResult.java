package com.itinordic.interop.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Charles Chigoriwa
 */
public class MappingResult implements Serializable{

    private Map<DataElement, Set<Option>> mapped = new HashMap<>();
    private Set<DataElement> unmappedDataElements = new HashSet<>();
    private DataSet inputDataSet;
    

    public Map<DataElement, Set<Option>> getMapped() {
        return mapped;
    }

    public void setMapped(Map<DataElement, Set<Option>> mapped) {
        this.mapped = mapped;
    }

    public Set<DataElement> getUnmappedDataElements() {
        return unmappedDataElements;
    }

    public void setUnmappedDataElements(Set<DataElement> unmappedDataElements) {
        this.unmappedDataElements = unmappedDataElements;
    }

    public DataSet getInputDataSet() {
        return inputDataSet;
    }

    public void setInputDataSet(DataSet inputDataSet) {
        this.inputDataSet = inputDataSet;
    }
    
    public void addToMapped(DataElement dataElement, Option option){
        Set<Option> options=mapped.get(dataElement);
        if(options==null){
            options=new HashSet<>();
            mapped.put(dataElement, options);
        }        
        options.add(option);
    }
    
    public void addToUnmapped(DataElement dataElement){
        unmappedDataElements.add(dataElement);
    }
    
    public Set<Option> getMappedOption(DataElement dataElement){
        return mapped.get(dataElement);
    }
        
}
