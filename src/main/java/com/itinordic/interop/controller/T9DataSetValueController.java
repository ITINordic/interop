package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.dao.DataSetValueDao;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
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
public class T9DataSetValueController {

  
    @Autowired
    private DataSetValueDao dataSetValueDao;

    @RequestMapping(value = "/admin/report/t9DataSetValues", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        model.addAttribute("dataSetValues", dataSetValueDao.findDataSetValueElements());       
        return "t9DataSetValue/t9DataSetValues";
    }

  
}
