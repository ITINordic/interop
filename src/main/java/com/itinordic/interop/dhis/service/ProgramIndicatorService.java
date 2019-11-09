package com.itinordic.interop.dhis.service;

import com.itinordic.interop.dhis.ProgramIndicator;

/**
 *
 * @author Charles Chigoriwa
 */
public interface ProgramIndicatorService {
    
    public ProgramIndicator getIndicatorByCode(String code);
    
    public void saveProgramIndicator(ProgramIndicator programIndicator);
        
    public ProgramIndicator getIndicatorByName(String name);
    
}
