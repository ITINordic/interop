package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.entity.DiagnosisForm;
import com.itinordic.interop.repo.DiagnosisFormRepository;
import com.itinordic.interop.service.DiagnosisFormService;
import com.itinordic.interop.util.PageUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/admin/diagnosis/forms", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        Page<DiagnosisForm> citiesPage = diagnosisFormService.findDiagnosisForms(searchDto, "id",true, 10);
        model.addAttribute("forms", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "diagnosisForm/diagnosisForms";
    }
    
    
    @RequestMapping(value = "/admin/diagnosis/forms/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
       return "redirect:/admin/diagnosis/forms";

    }

    @RequestMapping(value = "/admin/diagnosis/forms/upload", method = RequestMethod.POST)
    public String upload(MultipartFile csvInput, Principal principal, Model model) throws IOException {
        if (csvInput != null && !csvInput.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvInput.getBytes())))) {

            } catch (Exception ex) {
                System.out.print(ex);
            }

        }

        return "redirect:/admin/diagnosis/forms";

    }

}
