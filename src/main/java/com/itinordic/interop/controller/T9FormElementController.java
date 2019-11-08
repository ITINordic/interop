package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9FormElementSearchDto;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.repo.T9FormElementRepository;
import com.itinordic.interop.service.T9FormElementService;
import com.itinordic.interop.util.CategoryCombo;
import com.itinordic.interop.util.CategoryOptionCombo;
import com.itinordic.interop.util.DataElement;
import com.itinordic.interop.util.DataSet;
import com.itinordic.interop.util.DataSetElement;
import com.itinordic.interop.util.NhisDataSetRestUtility;
import com.itinordic.interop.util.PageUtil;
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

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class T9FormElementController {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private T9FormElementRepository t9FormElementRepository;
    @Autowired
    private T9DataElementRepository t9DataElementRepository;
    @Autowired
    private T9FormElementService t9FormElementService;

    @RequestMapping(value = "/admin/t9/formElements", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9FormElementSearchDto searchDto) {
        Page<T9FormElement> formElementsPage = t9FormElementService.findT9FormElements(searchDto, "id", false, 10);
        model.addAttribute("formElements", formElementsPage);
        PageUtil.injectPageAspects(model, formElementsPage);
        return "t9FormElement/t9FormElements";
    }
    
    
   

    @RequestMapping(value = "/admin/t9/formElements/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {
        DataSet dataSet = NhisDataSetRestUtility.getDataSet();
        List<DataSetElement> dataSetElements = dataSet.getDataSetElements();
        for (DataSetElement dataSetElement : dataSetElements) {
            DataElement dataElement = dataSetElement.getDataElement();
            CategoryCombo categoryCombo = dataElement.getCategoryCombo();
            T9DataElement t9DataElement=t9DataElementRepository.findByDhisId(dataElement.getId());
            if (categoryCombo != null) {
                List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                    for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {
                        String categoryOptionComboId = categoryOptionCombo.getId();                        
                        T9FormElement t9FormElement=t9FormElementRepository.findByDataElementAndCategoryOptionComboId(t9DataElement, categoryOptionComboId);
                        if(t9FormElement==null){
                            t9FormElement=new T9FormElement();
                            t9FormElement.setCategoryOptionComboId(categoryOptionComboId);
                            t9FormElement.setDataElement(t9DataElement);
                            t9FormElementRepository.save(t9FormElement);
                        }
                    }
                }

            }
        }

        return "redirect:/admin/t9/formElements";

    }


}
