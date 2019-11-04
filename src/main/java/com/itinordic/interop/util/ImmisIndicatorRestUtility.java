package com.itinordic.interop.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Charles Chigoriwa
 */
public class ImmisIndicatorRestUtility {

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

    public static void main(String[] args) {

        MappingResult mappingResult = MappingRestUtility.getMappingResult();
        DataSet dataSet = mappingResult.getInputDataSet();

        for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
            DataElement dataElement = dataSetElement.getDataElement();
            CategoryCombo categoryCombo = dataElement.getCategoryCombo();
            if (categoryCombo != null) {
                List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                    for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {

                    }
                }

            }
        }

    }

    public static ProgramIndicator getProgramIndicator(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options) {
        ProgramIndicator programIndicator = new ProgramIndicator();
        programIndicator.setName(getIndicatorName(dataElement, categoryOptionCombo));
        programIndicator.setExpression("V{event_count}");
        programIndicator.setFilter(getFilter(dataElement, categoryOptionCombo, options));
        programIndicator.setCode(dataElement.getId() + categoryOptionCombo.getId());
        programIndicator.setAggregationType("Count");
        programIndicator.setAnalyticsType("Event");
        return programIndicator;
    }

    public static String getFilter(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options) {
        String filter = "";

        if (options.size() == 1) {

        } else {
            filter+="(";
            for (Option option : options) {
            }
            filter+=")";            
        }

        return filter;
    }

    public static String getIndicatorName(DataElement dataElement, CategoryOptionCombo categoryOptionCombo) {
        String name = dataElement.getName().split("-")[1].trim();
        String categoryOptionComboId = categoryOptionCombo.getId();
        String suffix = "";
        if (categoryOptionComboId.equals(DEFAULT)) {
            suffix = "default";
        } else if (categoryOptionComboId.equals(UNDER_1_A)) {
            suffix = "Under 1 year With A outcome";
        } else if (categoryOptionComboId.equals(UNDER_1_C)) {
            suffix = "Under 1 year With C outcome";
        } else if (categoryOptionComboId.equals(UNDER_1_D)) {
            suffix = "Under 1 year With D outcome";
        } else if (categoryOptionComboId.equals(UNDER_1_T)) {
            suffix = "Under 1 year With T outcome";
        } else if (categoryOptionComboId.equals(_1_TO_4_A)) {
            suffix = "1 to 4 years With A outcome";
        } else if (categoryOptionComboId.equals(_1_TO_4_C)) {
            suffix = "1 to 4 years With C outcome";
        } else if (categoryOptionComboId.equals(_1_TO_4_D)) {
            suffix = "1 to 4 years With D outcome";
        } else if (categoryOptionComboId.equals(_1_TO_4_T)) {
            suffix = "1 to 4 years With T outcome";
        } else if (categoryOptionComboId.equals(_5_PLUS_A)) {
            suffix = "5 plus years With A outcome";
        } else if (categoryOptionComboId.equals(_5_PLUS_C)) {
            suffix = "5 plus years With C outcome";
        } else if (categoryOptionComboId.equals(_5_PLUS_D)) {
            suffix = "5 plus years With D outcome";
        } else if (categoryOptionComboId.equals(_5_PLUS_T)) {
            suffix = "5 plus years With T outcome";
        }
        return name + " " + suffix;
    }

}
