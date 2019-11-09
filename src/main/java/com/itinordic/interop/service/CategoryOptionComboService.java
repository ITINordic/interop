package com.itinordic.interop.service;

/**
 *
 * @author Charles Chigoriwa
 */
public interface CategoryOptionComboService {
    
    public static final String DEFAULT = "ylVaBBv3gZv";

    public static final String UNDER_1_A = "XVhXE8GBbNE";
    public static final String UNDER_1_C = "MohPCc2PzIj";
    public static final String UNDER_1_D = "C0gM7Xw6Ply";
    public static final String UNDER_1_T = "ZlDOdbPgREo";

    public static final String _1_TO_4_A = "JVsAEEbKwHJ";
    public static final String _1_TO_4_C = "lg4qk5sJusv";
    public static final String _1_TO_4_D = "SjKt2LmcIJF";
    public static final String _1_TO_4_T = "FQNs0dr09RE";

    public static final String _5_PLUS_A = "vtVARqV5pR7";
    public static final String _5_PLUS_C = "oNgL6EyJiQP";
    public static final String _5_PLUS_D = "qQ0nS3DX1vA";
    public static final String _5_PLUS_T = "X16NoX8ZPp3";
    
    public String getCategoryOptionComboId(String outcome, Integer age);
    
    //Total per age mapping (C or Cases or Total)
    public String getTotalCategoryOptionComboId(Integer age);
    
    
    public String getCategoryOptionComboName(String categoryOptionComboId);
    
}
