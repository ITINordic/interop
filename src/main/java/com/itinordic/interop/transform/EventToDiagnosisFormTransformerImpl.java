package com.itinordic.interop.transform;

import com.itinordic.interop.dhis.Event;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.repo.DiagnosisOrganizationUnitRepository;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
import com.itinordic.interop.service.CategoryOptionComboService;
import static com.itinordic.interop.util.DateUtility.parseLongDhisDate;
import com.itinordic.interop.util.GeneralUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Transactional
@Service
public class EventToDiagnosisFormTransformerImpl implements EventToDiagnosisFormTransformer{
    
     
    protected final Logger logger = LoggerFactory.getLogger(getClass());

      
    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private DiagnosisOrganizationUnitRepository diagnosisOrganizationUnitRepository;
    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;
    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;  
    @Autowired
    private CategoryOptionComboService categoryOptionComboService;
    
    @Transactional
    @Override
    @Nonnull
    public Optional<DiagnosisForm> transform(Event event) {
        DiagnosisForm diagnosisForm = diagnosisFormRepository.findByDhisId(event.getEvent());
        if (diagnosisForm == null) {
            diagnosisForm = new DiagnosisForm();
            diagnosisForm.setDhisId(event.getEvent());
        }

        String outcome = getValue(event, OUTCOME_DATA_ELEMENT_ID);
        String age = getValue(event, AGE_DATA_ELEMENT_ID);
        String diagnosisOptionCode = getValue(event, DIAGNOSIS_DATA_ELEMENT_ID);
        DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisCode(diagnosisOptionCode);
        if (diagnosisOption == null) {
            logger.info("DiagnosisOption with code {} not found", diagnosisOptionCode);
            return Optional.empty();
        }

        DiagnosisOrganizationUnit diagnosisOrgUnit = diagnosisOrganizationUnitRepository.findByDhisId(event.getOrgUnit());
        if (diagnosisOrgUnit == null) {
            logger.info("DiagnosisOrganizationUnit with id {} not found", event.getOrgUnit());
            return Optional.empty();
        }

        if (age == null) {
            logger.info("Null age for formId={}", event.getEvent());
            return Optional.empty();
        }

        T9OrganizationUnit t9OrganizationUnit;
        t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisId(diagnosisOrgUnit.getDhisId());

        if (t9OrganizationUnit == null) {
            if (diagnosisOrgUnit.hasDhisCode()) {
                t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisCode(diagnosisOrgUnit.getDhisCode());
            } else {
                t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisName(diagnosisOrgUnit.getDhisName());
            }
        }

        if (t9OrganizationUnit == null) {
            logger.info("T9OrganizationUnit not found for " + diagnosisOrgUnit);
        }

        double dAge = Double.valueOf(age);
        int intAge = (int) dAge;

        List<T9FormElement> mappedT9FormElements = getMappedT9FormElements(diagnosisOption, outcome, intAge);
        if (GeneralUtility.isEmpty(mappedT9FormElements)) {
            logger.info("MappedT9FormElements not found for option.code={}, age={}, outcome={}", diagnosisOption.getDhisCode(), age, outcome);
        }

        diagnosisForm.setDiagnosisOption(diagnosisOption);
        diagnosisForm.setOutcome(outcome);
        diagnosisForm.setAge(intAge);
        diagnosisForm.setDiagnosisOrgUnit(diagnosisOrgUnit);
        diagnosisForm.setT9OrgUnit(t9OrganizationUnit);
        diagnosisForm.setFormElements(mappedT9FormElements);
        diagnosisForm.setEventPeriod(getEventPeriod(event));
        diagnosisForm.setDeleted(event.isDeleted());
        diagnosisForm.setDhisCreated(parseLongDhisDate(event.getCreated()));
        diagnosisForm.setDhisEventDate(parseLongDhisDate(event.getEventDate()));
        diagnosisForm.setDhisLastUpdated(parseLongDhisDate(event.getLastUpdated()));
        diagnosisForm.setDhisCompletedDate(parseLongDhisDate(event.getCompletedDate()));
        diagnosisForm.setModificationDateTime(new Date());
        diagnosisForm.setStatus(event.getStatus());
        return Optional.of(diagnosisForm);
    }
    
    @Transactional
    @Override
    public List<T9FormElement> computeMappedT9FormElements(DiagnosisForm diagnosisForm) {
        return getMappedT9FormElements(diagnosisForm.getDiagnosisOption(), diagnosisForm.getOutcome(), diagnosisForm.getAge());
    }
    
     private List<T9FormElement> getMappedT9FormElements(DiagnosisOption diagnosisOption, String outcome, Integer age) {
        Set<T9FormElement> mappedT9FormElements = new HashSet<>();

        List<T9DataElement> dataElements = diagnosisOption.getDataElements();
        for (T9DataElement dataElement : dataElements) {
            //Default mapping/Bed Days/No categories
            if (dataElement.hasSingleFormElement() && dataElement.getFirstFormElement().getCategoryOptionComboId().equals(CategoryOptionComboService.DEFAULT)) {
                mappedT9FormElements.add(dataElement.getFirstFormElement());
            } else {
                //Outcome and age mapping
                String mappedOutcomeAgeCategoryOptionComboId = categoryOptionComboService.getCategoryOptionComboId(outcome, age);
                T9FormElement outcomeAgeT9FormElement = dataElement.getT9FormElement(mappedOutcomeAgeCategoryOptionComboId);
                if (outcomeAgeT9FormElement != null) {
                    mappedT9FormElements.add(outcomeAgeT9FormElement);
                }

                //Total per age mapping (C or Cases or Total)
                String mappedAgeAnyOutcomeCategoryOptionComboId = categoryOptionComboService.getTotalCategoryOptionComboId(age);
                T9FormElement ageAnyOutcomeT9FormElement = dataElement.getT9FormElement(mappedAgeAnyOutcomeCategoryOptionComboId);
                if (ageAnyOutcomeT9FormElement != null) {
                    mappedT9FormElements.add(ageAnyOutcomeT9FormElement);
                }
            }
        }

        return new ArrayList<>(mappedT9FormElements);

    }
     
   

    
    private String getEventPeriod(Event event) {
        String[] eventDates = event.getEventDate().split("-");
        return eventDates[0] + eventDates[1];
    }
    
    private String getValue(Event event, String dataElementId) {
        return event.getDataValues().stream().filter(dv -> dv.getDataElement().equals(dataElementId)).map(dv -> dv.getValue()).findFirst().orElse(null);
    }

}
