package com.itinordic.interop.service;

/**
 *
 * @author Charles Chigoriwa
 */
public interface CategoryOptionComboService {
    
    public static final String DEFAULT = "ylVaBBv3gZv";

    public static final String A_UNDER_1 = "XVhXE8GBbNE";
    public static final String C_UNDER_1 = "MohPCc2PzIj";
    public static final String D_UNDER_1 = "C0gM7Xw6Ply";
    public static final String T_UNDER_1 = "ZlDOdbPgREo";

    public static final String A_1_TO_4 = "JVsAEEbKwHJ";
    public static final String C_1_TO_4 = "lg4qk5sJusv";
    public static final String D_1_TO_4 = "SjKt2LmcIJF";
    public static final String T_1_TO_4 = "FQNs0dr09RE";

    public static final String A_5_PLUS = "vtVARqV5pR7";
    public static final String C_5_PLUS = "oNgL6EyJiQP";
    public static final String D_5_PLUS = "qQ0nS3DX1vA";
    public static final String T_5_PLUS = "X16NoX8ZPp3";
    
    public String getCategoryOptionComboId(String outcome, Integer age);
    
    //Total per age mapping (C or Cases or Total)
    public String getTotalCategoryOptionComboId(Integer age);
    
    
    public String getCategoryOptionComboName(String categoryOptionComboId);
    
}
