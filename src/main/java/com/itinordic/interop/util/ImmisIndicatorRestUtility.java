package com.itinordic.interop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
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

    public static void main(String[] args) throws JsonProcessingException {
        saveIndicators();
    }

    public static void saveIndicators() {
        List<ProgramIndicator> programIndicators = getProgramIndicators();

        for (ProgramIndicator programIndicator : programIndicators) {
            try{
            String uri = "https://dev.itin.africa/immis/api/programIndicators.json?strategy=CREATE";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("cchigoriwa", "Dhis123#");
            HttpEntity<ProgramIndicator> entity = new HttpEntity<>(programIndicator, headers);
            ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
            System.out.println(result);
            }catch(HttpClientErrorException ex){
                if(ex.getStatusCode().equals(HttpStatus.CONFLICT)){
                    System.out.println("Conflict "+programIndicator);
                }else{
                    throw ex;
                }
            }
        }

    }
    
    public static ProgramIndicator getIndicatorByName(String name){
        String uri = "https://dev.itin.africa/immis/api/programIndicators?paging=false&filter=name:eq:"+name;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ProgramIndicatorList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, ProgramIndicatorList.class);
        ProgramIndicatorList programIndicatorList= result.getBody();
        if(programIndicatorList!=null && programIndicatorList.getProgramIndicators()!=null && !programIndicatorList.getProgramIndicators().isEmpty()){
            return programIndicatorList.getProgramIndicators().get(0);
        }else{
            return null;
        }
    }
    
    public static ProgramIndicator getIndicatorByCode(String code){
        String uri = "https://dev.itin.africa/immis/api/programIndicators?paging=false&filter=code:eq:"+code;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ProgramIndicatorList> result = restTemplate.exchange(uri, HttpMethod.GET, entity, ProgramIndicatorList.class);
        ProgramIndicatorList programIndicatorList= result.getBody();
        if(programIndicatorList!=null && programIndicatorList.getProgramIndicators()!=null && !programIndicatorList.getProgramIndicators().isEmpty()){
            return programIndicatorList.getProgramIndicators().get(0);
        }else{
            return null;
        }
    }

    protected static void saveSampleTestIndicator() {
        ProgramIndicator programIndicator = new ProgramIndicator();
        String id = CodeGenerator.generateUid();
        programIndicator.setId(id);
        programIndicator.setProgram(ImmisProgramRestUtility.getProgram());
        programIndicator.setName("To be removed for testing");
        programIndicator.setShortName("To be removed for testing");
        programIndicator.setExpression("V{event_count}");
        programIndicator.setAggregationType("COUNT");
        programIndicator.setAnalyticsType("EVENT");

        String uri = "https://dev.itin.africa/immis/api/programIndicators.json?strategy=CREATE";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("cchigoriwa", "Dhis123#");
        HttpEntity<ProgramIndicator> entity = new HttpEntity<>(programIndicator, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class);
        System.out.println(result);
    }

    public static List<ProgramIndicator> getProgramIndicators() {
        Program program = ImmisProgramRestUtility.getProgram();
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
                            programIndicators.add(getProgramIndicator(dataElement, categoryOptionCombo, options, program));
                        }
                    }

                }
            }
        }
        return programIndicators;
    }

    public static ProgramIndicator getProgramIndicator(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options, Program program) {
        ProgramIndicator programIndicator = new ProgramIndicator();
        String id = CodeGenerator.generateUid();
        programIndicator.setId(id);
        programIndicator.setProgram(program);
        String name = getIndicatorName(dataElement, categoryOptionCombo);
        programIndicator.setName(name);
        programIndicator.setShortName(GeneralUtility.getSubstring(name.replaceAll("year", "yr"),50));
        programIndicator.setExpression("V{event_count}");
        programIndicator.setFilter(getFilter(dataElement, categoryOptionCombo, options));
        programIndicator.setCode(dataElement.getId() + categoryOptionCombo.getId());
        programIndicator.setAggregationType("COUNT");
        programIndicator.setAnalyticsType("EVENT");
        return programIndicator;
    }

    public static String getFilter(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options) {
        String filter = "";

        filter += "(";
        int i = 0;
        for (Option option : options) {
            if (i++ > 0) {
                filter += " or ";
            }
            filter += "#{HWvCCLjpiWG.PvciLByskeE} == '" + option.getCode() + "'";
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
        return name + " " + suffix;
    }

}
