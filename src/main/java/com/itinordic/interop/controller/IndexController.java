package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.service.T9DataElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class IndexController {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private T9DataElementService t9DataElementService;
    
    @GetMapping("/")
    public String index(Model model,T9DataElementSearchDto dataElementSearchDto){
        dataElementSearchDto.setNoOptions(true);
        long unmappedDataElementCount=t9DataElementService.getT9DataElementCount(dataElementSearchDto);
        model.addAttribute("unmappedDataElementCount", unmappedDataElementCount);
        model.addAttribute("alertUnmappedDataElements", unmappedDataElementCount > 0);
        
        return "index";
    }
}
