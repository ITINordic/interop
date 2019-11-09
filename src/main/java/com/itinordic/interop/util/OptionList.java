package com.itinordic.interop.util;

import com.itinordic.interop.dhis.Option;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class OptionList extends PageableList{
    
    private List<Option> options;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    
    
}
