package com.itinordic.interop.util;

import com.itinordic.interop.dhis.ProgramIndicator;
import java.util.List;

/**
 *
 * @author Charles Chigoriwa
 */
public class ProgramIndicatorList extends PageableList{
    
    private List<ProgramIndicator> programIndicators;

    public List<ProgramIndicator> getProgramIndicators() {
        return programIndicators;
    }

    public void setProgramIndicators(List<ProgramIndicator> programIndicators) {
        this.programIndicators = programIndicators;
    }
    
    
    
    
    
}
