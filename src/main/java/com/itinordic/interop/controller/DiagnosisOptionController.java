package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisOptionSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.service.DiagnosisOptionService;
import com.itinordic.interop.util.ImmisOptionSetRestUtility;
import com.itinordic.interop.util.Option;
import com.itinordic.interop.util.OptionSet;
import com.itinordic.interop.util.PageUtil;
import com.itinordic.interop.util.Select2Object;
import com.itinordic.interop.util.Select2ObjectBag;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class DiagnosisOptionController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private DiagnosisOptionService diagnosisOptionService;

    @RequestMapping(value = "/admin/diagnosis/options", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisOptionSearchDto searchDto) {
        Page<DiagnosisOption> optionsPage = diagnosisOptionService.findDiagnosisOptions(searchDto, "dhisName", false, 10);
        model.addAttribute("options", optionsPage);
        PageUtil.injectPageAspects(model, optionsPage);
        return "diagnosisOption/diagnosisOptions";
    }
    
    @RequestMapping(value = "/admin/diagnosis/options/unmapped", method = RequestMethod.GET)
    public String getUnmapped(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisOptionSearchDto searchDto) {
        searchDto.setNoDataElements(true);
        Page<DiagnosisOption> optionsPage = diagnosisOptionService.findDiagnosisOptions(searchDto, "dhisName", false, 10);
        model.addAttribute("options", optionsPage);
        PageUtil.injectPageAspects(model, optionsPage);
        return "diagnosisOption/unmappedDiagnosisOptions";
    }

    @RequestMapping(value = "/admin/diagnosis/options/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {
        OptionSet optionSet = ImmisOptionSetRestUtility.getOptionSet();
        List<Option> options = optionSet.getOptions();
        for (Option option : options) {
            DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisId(option.getId());
            if (diagnosisOption == null) {
                diagnosisOption = new DiagnosisOption();
                diagnosisOption.setDhisCode(option.getCode());
                diagnosisOption.setDhisName(option.getName());
                diagnosisOption.setDhisId(option.getId());
                diagnosisOptionRepository.save(diagnosisOption);
            }
        }
        return "redirect:/admin/diagnosis/options";

    }

    @ResponseBody
    @RequestMapping(value = "/admin/diagnosis/optionsJson", method = RequestMethod.GET)
    public Select2ObjectBag getStationsInJson(DiagnosisOptionSearchDto searchDto) {
        Select2ObjectBag bag = new Select2ObjectBag();
        Page<DiagnosisOption> optionsPage = diagnosisOptionService.findDiagnosisOptions(searchDto, "dhisName", false, 10);
        bag.setMore(optionsPage.hasNext());
        for (DiagnosisOption option : optionsPage.getContent()) {
            bag.add(new Select2Object(option.getId().toString(), option.getDhisName()));
        }
        return bag;
    }

}
