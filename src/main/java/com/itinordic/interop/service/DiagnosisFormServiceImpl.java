package com.itinordic.interop.service;

import com.itinordic.interop.criteria.DiagnosisFormPredicateUtil;
import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.criteria.T9DataElementPredicateUtil;
import com.itinordic.interop.criteria.T9DataElementSearchDto;
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
import static com.itinordic.interop.util.DateUtility.parseLongDhisDate;
import com.itinordic.interop.util.GeneralUtility;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Charles Chigoriwa
 */
@Service
public class DiagnosisFormServiceImpl implements DiagnosisFormService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private DiagnosisOrganizationUnitRepository diagnosisOrganizationUnitRepository;
    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;
    @Autowired
    private CategoryOptionComboService categoryOptionComboService;

    @Override
    public List<T9FormElement> computeMappedT9FormElements(DiagnosisForm diagnosisForm) {
        return getMappedT9FormElements(diagnosisForm.getDiagnosisOption(), diagnosisForm.getOutcome(), diagnosisForm.getAge());
    }

    @Override
    public Page<DiagnosisForm> findDiagnosisForms(DiagnosisFormSearchDto diagnosisFormSearchDto, String orderField, boolean desc, Integer pageSize) {
        Sort.Direction sortDirection = desc ? Sort.Direction.DESC : Sort.Direction.ASC;
        int pageNumber = diagnosisFormSearchDto.getPageNumber();
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortDirection, orderField);

        Predicate predicate = DiagnosisFormPredicateUtil.getPredicate(diagnosisFormSearchDto);
        if (predicate != null) {
            return diagnosisFormRepository.findAll(predicate, pageRequest);
        } else {
            return diagnosisFormRepository.findAll(pageRequest);
        }
    }

    @Override
    public long getDiagnosisFormCount(DiagnosisFormSearchDto diagnosisFormSearchDto) {
        if (diagnosisFormSearchDto == null) {
            return diagnosisFormRepository.count();
        }
        Predicate predicate = DiagnosisFormPredicateUtil.getPredicate(diagnosisFormSearchDto);
        if (predicate == null) {
            return diagnosisFormRepository.count();
        }

        return diagnosisFormRepository.count(predicate);
    }

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
            if (!GeneralUtility.isEmpty(diagnosisOrgUnit.getDhisCode())) {
                t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisCode(diagnosisOrgUnit.getDhisCode());
            } else {
                t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisName(diagnosisOrgUnit.getDhisName());
            }
        }

        if (t9OrganizationUnit == null) {
            logger.info("T9OrganizationUnit not found for " + diagnosisOrgUnit);
            return Optional.empty();
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
        return Optional.of(diagnosisForm);
    }

    private String getValue(Event event, String dataElementId) {
        return event.getDataValues().stream().filter(dv -> dv.getDataElement().equals(dataElementId)).map(dv -> dv.getValue()).findFirst().orElse(null);
    }

    private List<T9FormElement> getMappedT9FormElements(DiagnosisOption diagnosisOption, String outcome, Integer age) {
        Set<T9FormElement> mappedT9FormElements = new HashSet<>();

        List<T9DataElement> dataElements = diagnosisOption.getDataElements();
        for (T9DataElement dataElement : dataElements) {
            List<T9FormElement> formElements = dataElement.getFormElements();
            //Default mapping/Bed Days
            if (formElements.size() == 1 && formElements.get(0).getCategoryOptionComboId().equals(CategoryOptionComboService.DEFAULT)) {
                mappedT9FormElements.add(formElements.get(0));
            } else {
                //Outcome and age mapping
                String mappedCategoryOptionComboId = categoryOptionComboService.getCategoryOptionComboId(outcome, age);
                T9FormElement ageOutcomeT9FormElement = getT9FormElement(formElements, mappedCategoryOptionComboId);
                if (ageOutcomeT9FormElement != null) {
                    mappedT9FormElements.add(ageOutcomeT9FormElement);
                }

                //Total per age mapping (C or Cases or Total)
                String mappedTotalCategoryOptionComboId = categoryOptionComboService.getTotalCategoryOptionComboId(age);
                T9FormElement totalAgeT9FormElement = getT9FormElement(formElements, mappedTotalCategoryOptionComboId);
                if (totalAgeT9FormElement != null) {
                    mappedT9FormElements.add(totalAgeT9FormElement);
                }
            }
        }

        return new ArrayList<>(mappedT9FormElements);

    }

    private String getEventPeriod(Event event) {
        String[] eventDates = event.getEventDate().split("-");
        return eventDates[0] + eventDates[1];
    }

    private T9FormElement getT9FormElement(List<T9FormElement> formElements, String categoryOptionComboId) {
        for (T9FormElement formElement : formElements) {
            if (formElement.getCategoryOptionComboId().equals(categoryOptionComboId)) {
                return formElement;
            }
        }
        return null;
    }

}
