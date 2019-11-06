package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public class SimpleGeneralUtility {
    
    
    public static String getSubstring(String string,int maxLength){
        if(string==null || string.length()<=maxLength){
            return string;
        }
        
        return string.substring(0,maxLength);
    }
    
    
    public static void main(String[] args){
        System.out.println(getSubstring("Inpatient Retinal detachments and breaks 1 to 4 yrs with C", 50));
    }
    
}
