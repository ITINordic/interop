package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisOrgnUnitSearchDto;
import com.itinordic.interop.entity.DiagnosisOrganizationUnit;
import com.itinordic.interop.repo.DiagnosisOrganizationUnitRepository;
import com.itinordic.interop.service.DiagnosisOrgnUnitService;
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
public class DiagnosisOrgnUnitController {

    @Autowired
    private DiagnosisOrganizationUnitRepository diagnosisOrganizationUnitRepository;

    @Autowired
    private DiagnosisOrgnUnitService diagnosisOrgnUnitService;

    @RequestMapping(value = "/admin/diagnosis/organizationUnits", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisOrgnUnitSearchDto searchDto) {
        Page<DiagnosisOrganizationUnit> citiesPage = diagnosisOrgnUnitService.findDiagnosisOrganizationUnits(searchDto, "dhisName", false, 10);
        model.addAttribute("organizationUnits", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "diagnosisOrgUnit/diagnosisOrgnUnits";
    }
    
    
    @RequestMapping(value = "/admin/diagnosis/organizationUnits/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
       return "redirect:/admin/diagnosis/organizationUnits";

    }

    @RequestMapping(value = "/admin/diagnosis/organizationUnits/upload", method = RequestMethod.POST)
    public String upload(MultipartFile csvInput, Principal principal, Model model) throws IOException {
        if (csvInput != null && !csvInput.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvInput.getBytes())))) {

            } catch (Exception ex) {
                System.out.print(ex);
            }

        }

        return "redirect:/admin/diagnosis/organizationUnits";

    }

}
