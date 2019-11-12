package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.util.PageUtil;
import java.io.IOException;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.itinordic.interop.sync.LiteEventSyncService;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class DiagnosisFormController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisFormService diagnosisFormService;
    @Autowired
    private LiteEventSyncService eventSyncService;

    @RequestMapping(value = "/admin/diagnosis/forms", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        Page<DiagnosisForm> diagnosisFormPage = diagnosisFormService.findDiagnosisForms(searchDto, "id", true, 10);
        model.addAttribute("forms", diagnosisFormPage);
        PageUtil.injectPageAspects(model, diagnosisFormPage);
        return "diagnosisForm/diagnosisForms";
    }

    @RequestMapping(value = "/admin/diagnosis/forms/unmapped", method = RequestMethod.GET)
    public String getUnmapped(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        searchDto.setNoT9FormElements(true);
        Page<DiagnosisForm> diagnosisFormPage = diagnosisFormService.findDiagnosisForms(searchDto, "id", true, 10);
        model.addAttribute("forms", diagnosisFormPage);
        PageUtil.injectPageAspects(model, diagnosisFormPage);
        return "diagnosisForm/unmappedDiagnosisForms";
    }

    @RequestMapping(value = "/admin/diagnosis/forms/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
        eventSyncService.syncEvents();
        return "redirect:/admin/diagnosis/forms";
    }

}
