package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9OrgnUnitSearchDto;
import com.itinordic.interop.entity.T9OrganizationUnit;
import com.itinordic.interop.repo.T9OrganizationUnitRepository;
import com.itinordic.interop.service.T9OrgnUnitService;
import com.itinordic.interop.util.NhisOrganizationUnitRestUtility;
import com.itinordic.interop.util.OrganizationUnit;
import com.itinordic.interop.util.PageUtil;
import java.io.IOException;
import java.security.Principal;
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
public class T9OrgnUnitController {

    @Autowired
    private T9OrganizationUnitRepository t9OrganizationUnitRepository;

    @Autowired
    private T9OrgnUnitService t9OrgnUnitService;

    @RequestMapping(value = "/admin/t9/organizationUnits", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9OrgnUnitSearchDto searchDto) {
        Page<T9OrganizationUnit> organizationUnitsPage = t9OrgnUnitService.findT9OrganizationUnits(searchDto, "dhisName", false, 10);
        model.addAttribute("organizationUnits", organizationUnitsPage);
        PageUtil.injectPageAspects(model, organizationUnitsPage);
        return "t9OrgUnit/t9OrgnUnits";
    }
    
    
    @RequestMapping(value = "/admin/t9/organizationUnits/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {
        List<OrganizationUnit> organizationUnits = NhisOrganizationUnitRestUtility.getOrganizationUnits();
        for (OrganizationUnit organizationUnit : organizationUnits) {
            T9OrganizationUnit t9OrganizationUnit = t9OrganizationUnitRepository.findByDhisId(organizationUnit.getId());
            if (t9OrganizationUnit == null) {
                t9OrganizationUnit = new T9OrganizationUnit();
                t9OrganizationUnit.setDhisCode(organizationUnit.getCode());
                t9OrganizationUnit.setDhisName(organizationUnit.getName());
                t9OrganizationUnit.setDhisId(organizationUnit.getId());
                t9OrganizationUnit.setDhisShortName(organizationUnit.getShortName());
                t9OrganizationUnitRepository.save(t9OrganizationUnit);
            }
        }        
        return "redirect:/admin/t9/organizationUnits";
    }

   

}
