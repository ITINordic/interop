package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9OrgnUnitSearchDto;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
import com.itinordic.interop.service.T9OrgnUnitService;
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
public class T9OrgnUnitController {

    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;

    @Autowired
    private T9OrgnUnitService t9OrgnUnitService;

    @RequestMapping(value = "/admin/t9/organizationUnits", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9OrgnUnitSearchDto searchDto) {
        Page<T9OrganizationUnit> citiesPage = t9OrgnUnitService.findT9OrganizationUnits(searchDto, "dhisName", false, 10);
        model.addAttribute("organizationUnits", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "t9OrgUnit/t9OrgnUnits";
    }
    
    
    @RequestMapping(value = "/admin/t9/organizationUnits/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
       return "redirect:/admin/t9/organizationUnits";

    }

    @RequestMapping(value = "/admin/t9/organizationUnits/upload", method = RequestMethod.POST)
    public String upload(MultipartFile csvInput, Principal principal, Model model) throws IOException {
        if (csvInput != null && !csvInput.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvInput.getBytes())))) {

            } catch (Exception ex) {
                System.out.print(ex);
            }

        }

        return "redirect:/admin/t9/organizationUnits";

    }

}
