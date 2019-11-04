package com.itinordic.interop.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

        List<ProgramIndicator> programIndicators = getProgramIndicators();

        for (ProgramIndicator programIndicator : programIndicators) {
            String uri = "https://dev.itin.africa/immis/api/programIndicators";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("cchigoriwa", "Dhis123#");
            HttpEntity<ProgramIndicator> entity = new HttpEntity<>(programIndicator, headers);
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            System.out.println(result);
        }

    }

    public static List<ProgramIndicator> getProgramIndicators() {
        List<ProgramIndicator> programIndicators = new ArrayList<>();
        MappingResult mappingResult = MappingRestUtility.getMappingResult();
        DataSet dataSet = mappingResult.getInputDataSet();
        for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
            DataElement dataElement = dataSetElement.getDataElement();
            Set<Option> options = mappingResult.getMappedOption(dataElement);
            if (options != null && !options.isEmpty()) {
                CategoryCombo categoryCombo = dataElement.getCategoryCombo();
                if (categoryCombo != null) {
                    List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                    if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                        for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {
                            programIndicators.add(getProgramIndicator(dataElement, categoryOptionCombo, options));
                        }
                    }

                }
            }
        }
        return programIndicators;
    }

    public static ProgramIndicator getProgramIndicator(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options) {
        ProgramIndicator programIndicator = new ProgramIndicator();
        programIndicator.setProgram("SXNeRfGsKc");
        String name = getIndicatorName(dataElement, categoryOptionCombo);
        programIndicator.setName(name);
        programIndicator.setShortName(name.replaceAll("year", "y"));
        programIndicator.setExpression("V{event_count}");
        programIndicator.setFilter(getFilter(dataElement, categoryOptionCombo, options));
        programIndicator.setCode(dataElement.getId() + categoryOptionCombo.getId());
        programIndicator.setAggregationType("Count");
        programIndicator.setAnalyticsType("Event");
        return programIndicator;
    }

    public static String getFilter(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options) {
        String filter = "";

        filter += "(";
        int i = 0;
        for (Option option : options) {
            if (i > 0) {
                filter += " or ";
            }
            filter += "#{HWvCCLjpiWG.PvciLByskeE} == '" + option.getCode() + "'";
            i++;
        }
        filter += ")";

        String categoryOptionComboId = categoryOptionCombo.getId();
        if (!categoryOptionComboId.equals(DEFAULT)) {
            filter += " and ";
        }
        if (categoryOptionComboId.equals(UNDER_1_A)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'A' and #{HWvCCLjpiWG.bl1Dflv1nag} < 1";
        } else if (categoryOptionComboId.equals(UNDER_1_C)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'C' and #{HWvCCLjpiWG.bl1Dflv1nag} < 1";
        } else if (categoryOptionComboId.equals(UNDER_1_D)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'D' and #{HWvCCLjpiWG.bl1Dflv1nag} < 1";
        } else if (categoryOptionComboId.equals(UNDER_1_T)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'T' and #{HWvCCLjpiWG.bl1Dflv1nag} < 1";
        } else if (categoryOptionComboId.equals(_1_TO_4_A)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'A' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 1 and #{HWvCCLjpiWG.bl1Dflv1nag} <= 4";
        } else if (categoryOptionComboId.equals(_1_TO_4_C)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'C' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 1 and #{HWvCCLjpiWG.bl1Dflv1nag} <= 4";
        } else if (categoryOptionComboId.equals(_1_TO_4_D)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'D' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 1 and #{HWvCCLjpiWG.bl1Dflv1nag} <= 4";
        } else if (categoryOptionComboId.equals(_1_TO_4_T)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'T' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 1 and #{HWvCCLjpiWG.bl1Dflv1nag} <= 4";
        } else if (categoryOptionComboId.equals(_5_PLUS_A)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'A' and #{HWvCCLjpiWG.bl1Dflv1nag} > 5";
        } else if (categoryOptionComboId.equals(_5_PLUS_C)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'C' and #{HWvCCLjpiWG.bl1Dflv1nag} > 5";
        } else if (categoryOptionComboId.equals(_5_PLUS_D)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'D' and #{HWvCCLjpiWG.bl1Dflv1nag} > 5";
        } else if (categoryOptionComboId.equals(_5_PLUS_T)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'T' and #{HWvCCLjpiWG.bl1Dflv1nag} > 5";
        }

        return filter;
    }

    public static String getIndicatorName(DataElement dataElement, CategoryOptionCombo categoryOptionCombo) {
        String name = "Inpatient " + dataElement.getName().split("-")[1].trim();
        String categoryOptionComboId = categoryOptionCombo.getId();
        String suffix = "";
        if (categoryOptionComboId.equals(DEFAULT)) {
            suffix = "default";
        } else if (categoryOptionComboId.equals(UNDER_1_A)) {
            suffix = "Under 1 year With A";
        } else if (categoryOptionComboId.equals(UNDER_1_C)) {
            suffix = "Under 1 year With C";
        } else if (categoryOptionComboId.equals(UNDER_1_D)) {
            suffix = "Under 1 year With D";
        } else if (categoryOptionComboId.equals(UNDER_1_T)) {
            suffix = "Under 1 year With T";
        } else if (categoryOptionComboId.equals(_1_TO_4_A)) {
            suffix = "1 to 4 years With A";
        } else if (categoryOptionComboId.equals(_1_TO_4_C)) {
            suffix = "1 to 4 years With C";
        } else if (categoryOptionComboId.equals(_1_TO_4_D)) {
            suffix = "1 to 4 years With D";
        } else if (categoryOptionComboId.equals(_1_TO_4_T)) {
            suffix = "1 to 4 years With T";
        } else if (categoryOptionComboId.equals(_5_PLUS_A)) {
            suffix = "5 plus years With A";
        } else if (categoryOptionComboId.equals(_5_PLUS_C)) {
            suffix = "5 plus years With C";
        } else if (categoryOptionComboId.equals(_5_PLUS_D)) {
            suffix = "5 plus years With D";
        } else if (categoryOptionComboId.equals(_5_PLUS_T)) {
            suffix = "5 plus years With T";
        }
        return name + " " + suffix;
    }

}
