package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisOptionSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.service.DiagnosisOptionService;
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
public class DiagnosisOptionController {

    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;

    @Autowired
    private DiagnosisOptionService diagnosisOptionService;

    @RequestMapping(value = "/admin/diagnosis/options", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisOptionSearchDto searchDto) {
        Page<DiagnosisOption> citiesPage = diagnosisOptionService.findDiagnosisOptions(searchDto, "dhisName", false, 10);
        model.addAttribute("options", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "diagnosisOption/diagnosisOptions";
    }
    
    
    @RequestMapping(value = "/admin/diagnosis/options/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
       return "redirect:/admin/diagnosis/options";

    }

    @RequestMapping(value = "/admin/diagnosis/options/upload", method = RequestMethod.POST)
    public String upload(MultipartFile csvInput, Principal principal, Model model) throws IOException {
        if (csvInput != null && !csvInput.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvInput.getBytes())))) {

            } catch (Exception ex) {
                System.out.print(ex);
            }

        }

        return "redirect:/admin/diagnosis/options";

    }

}
