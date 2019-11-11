package com.itinordic.interop.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service("utilityService")
public class UtilityServiceImpl implements UtilityService{
    
    @Override
    public String format(Long number){
        if(number==null){
            return null;
        }else{
            return String.format("%,d", number);
        }
        
    }
    
    @Override
    public String format(Integer number){
        if(number==null){
            return null;
        }else{
            return String.format("%,d", number);
        }
        
    }
    
}
