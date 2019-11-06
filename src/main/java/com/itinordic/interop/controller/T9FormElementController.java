package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9FormElementSearchDto;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.T9FormElementRepository;
import com.itinordic.interop.service.T9FormElementService;
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
public class T9FormElementController {

    @Autowired
    private T9FormElementRepository t9FormElementRepository;

    @Autowired
    private T9FormElementService t9FormElementService;

    @RequestMapping(value = "/admin/t9/formElements", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9FormElementSearchDto searchDto) {
        Page<T9FormElement> citiesPage = t9FormElementService.findT9FormElements(searchDto, "id", false, 10);
        model.addAttribute("formElements", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "t9FormElement/t9FormElements";
    }
    
    
    @RequestMapping(value = "/admin/t9/formElements/sync", method = RequestMethod.POST)
    public String sync(Principal principal, Model model) throws IOException {
       return "redirect:/admin/t9/formElements";

    }

    @RequestMapping(value = "/admin/t9/formElements/upload", method = RequestMethod.POST)
    public String upload(MultipartFile csvInput, Principal principal, Model model) throws IOException {
        if (csvInput != null && !csvInput.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(csvInput.getBytes())))) {

            } catch (Exception ex) {
                System.out.print(ex);
            }

        }

        return "redirect:/admin/t9/formElements";

    }

}
