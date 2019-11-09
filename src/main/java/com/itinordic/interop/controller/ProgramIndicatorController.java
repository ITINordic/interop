package com.itinordic.interop.controller;

import com.itinordic.interop.dhis.CategoryCombo;
import com.itinordic.interop.dhis.CategoryOptionCombo;
import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.DataSet;
import com.itinordic.interop.dhis.DataSetElement;
import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.Program;
import com.itinordic.interop.dhis.ProgramIndicator;
import com.itinordic.interop.dhis.service.MappingService;
import com.itinordic.interop.dhis.service.ProgramIndicatorService;
import com.itinordic.interop.dhis.service.ProgramService;
import static com.itinordic.interop.util.CategoryOptionComboUtility.*;
import static com.itinordic.interop.util.CategoryOptionComboUtility.getCategoryOptionComboName;
import com.itinordic.interop.util.CodeGenerator;
import com.itinordic.interop.util.MappingResult;
import com.itinordic.interop.util.SimpleGeneralUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author developer
 */
@Controller
public class ProgramIndicatorController {

    @Autowired
    private ProgramIndicatorService programIndicatorService;
    @Autowired
    private MappingService mappingService;
    @Autowired
    private ProgramService programService;

    public void saveIndicators() {
        List<ProgramIndicator> programIndicators = getProgramIndicators();

        System.out.println("ProgramIndicators " + programIndicators.size());

        for (ProgramIndicator programIndicator : programIndicators) {
            try {
                programIndicatorService.saveProgramIndicator(programIndicator);
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().equals(HttpStatus.CONFLICT)) {
                    System.out.println("Conflict " + programIndicator);
                } else {
                    throw ex;
                }
            }
        }

    }

    public List<ProgramIndicator> getProgramIndicators() {
        Program program = programService.getProgram();
        List<ProgramIndicator> programIndicators = new ArrayList<>();
        MappingResult mappingResult = mappingService.getMappingResult();
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

    public ProgramIndicator getProgramIndicator(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options, Program program) {
        ProgramIndicator programIndicator = new ProgramIndicator();
        String id = CodeGenerator.generateUid();
        programIndicator.setId(id);
        programIndicator.setProgram(program);
        String name = getIndicatorName(dataElement, categoryOptionCombo);
        programIndicator.setName(name);
        programIndicator.setShortName(SimpleGeneralUtility.getSubstring(name.replaceAll("year", "yr"), 50));
        programIndicator.setExpression("V{event_count}");
        programIndicator.setFilter(getFilter(dataElement, categoryOptionCombo, options));
        programIndicator.setCode(dataElement.getId() + categoryOptionCombo.getId());
        programIndicator.setAggregationType("COUNT");
        programIndicator.setAnalyticsType("EVENT");
        return programIndicator;
    }

    public String getFilter(DataElement dataElement, CategoryOptionCombo categoryOptionCombo, Set<Option> options) {
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
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'A' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 5";
        } else if (categoryOptionComboId.equals(_5_PLUS_C)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'C' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 5";
        } else if (categoryOptionComboId.equals(_5_PLUS_D)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'D' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 5";
        } else if (categoryOptionComboId.equals(_5_PLUS_T)) {
            filter += "#{HWvCCLjpiWG.emFE351TuNs} == 'T' and #{HWvCCLjpiWG.bl1Dflv1nag} >= 5";
        }

        return filter;
    }

    public String getIndicatorName(DataElement dataElement, CategoryOptionCombo categoryOptionCombo) {
        String name = "Inpatient " + dataElement.getName().split("-")[1].trim();
        String categoryOptionComboId = categoryOptionCombo.getId();
        return name + " " + getCategoryOptionComboName(categoryOptionComboId);
    }

}
