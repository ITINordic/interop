package com.itinordic.interop.util;

/**
 *
 * @author Charles Chigoriwa
 */
public class CategoryOptionComboUtility {

    public static String DEFAULT = "ylVaBBv3gZv";

    public static String UNDER_1_A = "XVhXE8GBbNE";
    public static String UNDER_1_C = "MohPCc2PzIj";
    public static String UNDER_1_D = "C0gM7Xw6Ply";
    public static String UNDER_1_T = "ZlDOdbPgREo";

    public static String _1_TO_4_A = "JVsAEEbKwHJ";
    public static String _1_TO_4_C = "lg4qk5sJusv";
    public static String _1_TO_4_D = "SjKt2LmcIJF";
    public static String _1_TO_4_T = "FQNs0dr09RE";

    public static String _5_PLUS_A = "vtVARqV5pR7";
    public static String _5_PLUS_C = "oNgL6EyJiQP";
    public static String _5_PLUS_D = "qQ0nS3DX1vA";
    public static String _5_PLUS_T = "X16NoX8ZPp3";

    public static String getCategoryOptionComboId(String outcome, Integer age) {
        if (age < 1 && outcome.equals("A")) {
            return UNDER_1_A;
        } else if (age < 1 && outcome.equals("C")) {
            return UNDER_1_C;
        } else if (age < 1 && outcome.equals("D")) {
            return UNDER_1_D;
        } else if (age < 1 && outcome.equals("T")) {
            return UNDER_1_T;
        } else if (age >= 1 && age <= 4 && outcome.equals("A")) {
            return _1_TO_4_A;
        } else if (age >= 1 && age <= 4 && outcome.equals("C")) {
            return _1_TO_4_C;
        } else if (age >= 1 && age <= 4 && outcome.equals("D")) {
            return _1_TO_4_D;
        } else if (age >= 1 && age <= 4 && outcome.equals("T")) {
            return _1_TO_4_T;
        } else if (age >= 5 && outcome.equals("A")) {
            return _5_PLUS_A;
        } else if (age >= 5 && outcome.equals("C")) {
            return _5_PLUS_C;
        } else if (age >= 5 && outcome.equals("D")) {
            return _5_PLUS_D;
        } else if (age >= 5 && outcome.equals("T")) {
            return _5_PLUS_T;
        }

        return null;
    }

    //Total per age mapping (C or Cases or Total)
    public static String getTotalCategoryOptionComboId(Integer age) {
        if (age < 1) {
            return UNDER_1_C;
        } else if (age >= 1 && age <= 4) {
            return _1_TO_4_C;
        } else if (age >= 5) {
            return _5_PLUS_C;
        }
        return null;
    }

    public static String getCategoryOptionComboName(String categoryOptionComboId) {
        String suffix = "";
        if (categoryOptionComboId.equals(DEFAULT)) {
            suffix = "default";
        } else if (categoryOptionComboId.equals(UNDER_1_A)) {
            suffix = "under 1 year with A";
        } else if (categoryOptionComboId.equals(UNDER_1_C)) {
            suffix = "under 1 year with C";
        } else if (categoryOptionComboId.equals(UNDER_1_D)) {
            suffix = "under 1 year with D";
        } else if (categoryOptionComboId.equals(UNDER_1_T)) {
            suffix = "under 1 year with T";
        } else if (categoryOptionComboId.equals(_1_TO_4_A)) {
            suffix = "1 to 4 years with A";
        } else if (categoryOptionComboId.equals(_1_TO_4_C)) {
            suffix = "1 to 4 years with C";
        } else if (categoryOptionComboId.equals(_1_TO_4_D)) {
            suffix = "1 to 4 years with D";
        } else if (categoryOptionComboId.equals(_1_TO_4_T)) {
            suffix = "1 to 4 years with T";
        } else if (categoryOptionComboId.equals(_5_PLUS_A)) {
            suffix = "5 plus years with A";
        } else if (categoryOptionComboId.equals(_5_PLUS_C)) {
            suffix = "5 plus years with C";
        } else if (categoryOptionComboId.equals(_5_PLUS_D)) {
            suffix = "5 plus years with D";
        } else if (categoryOptionComboId.equals(_5_PLUS_T)) {
            suffix = "5 plus years with T";
        }
        return suffix;
    }

}
