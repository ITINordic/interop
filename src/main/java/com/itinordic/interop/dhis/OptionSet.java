package com.itinordic.interop.dhis;

import java.util.List;

/**
 *
 * @author Charles Chigowa
 */
public class OptionSet {
    
    private String id;
    
    private List<Option> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "OptionSet{" + "id=" + id + ", options=" + options + '}';
    }
    
    
}
