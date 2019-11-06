package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.service.T9DataElementService;
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
public class T9DataElementController {

    @Autowired
    private T9DataElementRepository t9DataElementRepository;

    @Autowired
    private T9DataElementService t9DataElementService;

    @RequestMapping(value = "/admin/t9/dataElements", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9DataElementSearchDto searchDto) {
        Page<T9DataElement> citiesPage = t9DataElementService.findT9DataElements(searchDto, "dhisName", false, 10);
        model.addAttribute("dataElements", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "t9DataElement/t9DataElements";
    }
    
    
    @RequestMapping(value = "/admin/t9/dataElements/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
       return "redirect:/admin/t9/dataElements";

    }

    @RequestMapping(value = "/admin/t9/dataElements/upload", method = RequestMethod.POST)
    public String upload(MultipartFile csvInput, Principal principal, Model model) throws IOException {
        if (csvInput != null && !csvInput.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvInput.getBytes())))) {

            } catch (Exception ex) {
                System.out.print(ex);
            }

        }

        return "redirect:/admin/t9/dataElements";

    }

}
