package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisOptionSearchDto;
import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.service.DiagnosisOptionService;
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
    @Autowired
    private DiagnosisOptionService diagnosisOptionService;

    @GetMapping("/")
    public String index(Model model, T9DataElementSearchDto dataElementSearchDto, DiagnosisOptionSearchDto diagnosisOptionSearchDto) {
        dataElementSearchDto.setNoOptions(true);
        diagnosisOptionSearchDto.setNoDataElements(true);
        long unmappedDataElementCount = t9DataElementService.getT9DataElementCount(dataElementSearchDto);
        long unmappedOptionCount = diagnosisOptionService.getDiagnosisOptionCount(diagnosisOptionSearchDto);
        model.addAttribute("unmappedDataElementCount", unmappedDataElementCount);
        model.addAttribute("alertUnmappedDataElements", unmappedDataElementCount > 0);
        model.addAttribute("unmappedOptionCount", unmappedOptionCount);
        model.addAttribute("alertUnmappedOptions", unmappedOptionCount > 0);

        return "index";
    }
}
