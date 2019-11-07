package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
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
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.util.CategoryOptionComboUtility;
import com.itinordic.interop.util.Event;
import com.itinordic.interop.util.GeneralUtility;
import com.itinordic.interop.util.ImmisEventRestUtility;
import com.itinordic.interop.util.PageUtil;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class DiagnosisFormController {

    @Autowired
    private DiagnosisFormRepository diagnosisFormRepository;
    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private DiagnosisOrganizationUnitRepository diagnosisOrganizationUnitRepository;
    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;

    @RequestMapping(value = "/admin/diagnosis/forms", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        Page<DiagnosisForm> citiesPage = diagnosisFormService.findDiagnosisForms(searchDto, "id", true, 10);
        model.addAttribute("forms", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "diagnosisForm/diagnosisForms";
    }

    @RequestMapping(value = "/admin/diagnosis/forms/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {

        List<Event> events = ImmisEventRestUtility.getEvents();
        for (Event event : events) {
            DiagnosisForm diagnosisForm = diagnosisFormRepository.findByDhisId(event.getEvent());
            if (diagnosisForm == null) {

                String outcome = getValue(event, "emFE351TuNs");
                String age = getValue(event, "bl1Dflv1nag");
                String diagnosisOptionCode = getValue(event, "PvciLByskeE");
                DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisCode(diagnosisOptionCode);
                DiagnosisOrganizationUnit diagnosisOrgUnit = diagnosisOrganizationUnitRepository.findByDhisId(event.getOrgUnit());
                T9OrganizationUnit t9OrganizationUnit;
                t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisId(diagnosisOrgUnit.getDhisId());

                if (t9OrganizationUnit == null) {
                    if (!GeneralUtility.isEmpty(diagnosisOrgUnit.getDhisCode())) {
                        t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisCode(diagnosisOrgUnit.getDhisCode());
                    } else {
                        t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisName(diagnosisOrgUnit.getDhisName());
                    }
                }

                diagnosisForm = new DiagnosisForm();
                diagnosisForm.setDiagnosisOption(diagnosisOption);
                diagnosisForm.setOutcome(outcome);
                diagnosisForm.setAge(Integer.valueOf(age));
                diagnosisForm.setDhisId(event.getEvent());
                diagnosisForm.setDiagnosisOrgUnit(diagnosisOrgUnit);
                diagnosisForm.setT9OrgUnit(t9OrganizationUnit);
                diagnosisForm.setFormElements(getMappedT9FormElements(diagnosisOption, outcome, Integer.valueOf(age)));
                diagnosisForm.setEventPeriod(getEventPeriod(event));
                diagnosisFormRepository.save(diagnosisForm);
            }

        }

        return "redirect:/admin/diagnosis/forms";

    }

    private String getValue(Event event, String dataElementId) {
        return event.getDataValues().stream().filter(dv -> dv.getDataElement().equals(dataElementId)).map(dv -> dv.getValue()).findFirst().orElse(null);
    }

    private List<T9FormElement> getMappedT9FormElements(DiagnosisOption diagnosisOption, String outcome, Integer age) {
        List<T9FormElement> mappedT9FormElements = new ArrayList<>();

        List<T9DataElement> dataElements = diagnosisOption.getDataElements();
        for (T9DataElement dataElement : dataElements) {
            List<T9FormElement> formElements = dataElement.getFormElements();
            if (formElements.size() == 1 && formElements.get(0).getCategoryOptionComboId().equals(CategoryOptionComboUtility.DEFAULT)) {
                mappedT9FormElements.add(formElements.get(0));
            } else {
                String mappedCategoryOptionComboId = CategoryOptionComboUtility.getCategoryOptionComboId(outcome, age);
                for (T9FormElement formElement : formElements) {
                    if (formElement.getCategoryOptionComboId().equals(mappedCategoryOptionComboId)) {
                        mappedT9FormElements.add(formElement);
                        break;
                    }
                }
            }
        }

        return mappedT9FormElements;

    }
    
    private String getEventPeriod(Event event){
        String[] eventDates=event.getEventDate().split("-");
        return eventDates[0]+eventDates[1];
    }
}
