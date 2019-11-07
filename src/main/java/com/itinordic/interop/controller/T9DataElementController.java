package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.service.T9DataElementService;
import com.itinordic.interop.util.DataElement;
import com.itinordic.interop.util.DataSet;
import com.itinordic.interop.util.DataSetElement;
import com.itinordic.interop.util.GeneralUtility;
import com.itinordic.interop.util.MappingRestUtility;
import com.itinordic.interop.util.MappingResult;
import com.itinordic.interop.util.NhisDataSetRestUtility;
import com.itinordic.interop.util.Option;
import com.itinordic.interop.util.PageUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private T9DataElementService t9DataElementService;

    @RequestMapping(value = "/admin/t9/dataElements", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9DataElementSearchDto searchDto) {
        Page<T9DataElement> citiesPage = t9DataElementService.findT9DataElements(searchDto, "modificationDateTime", true, 10);
        model.addAttribute("dataElements", citiesPage);
        PageUtil.injectPageAspects(model, citiesPage);
        return "t9DataElement/t9DataElements";
    }

    @RequestMapping(value = "/admin/t9/dataElements/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {
        DataSet dataSet = NhisDataSetRestUtility.getDataSet();
        List<DataSetElement> dataSetElements = dataSet.getDataSetElements();
        for (DataSetElement dataSetElement : dataSetElements) {
            DataElement dataElement = dataSetElement.getDataElement();
            T9DataElement t9DataElement = t9DataElementRepository.findByDhisId(dataElement.getId());
            if (t9DataElement == null) {
                t9DataElement = new T9DataElement();
                t9DataElement.setDhisCode(dataElement.getCode());
                t9DataElement.setDhisName(dataElement.getName());
                t9DataElement.setDhisId(dataElement.getId());
                t9DataElementRepository.save(t9DataElement);
            }
        }

        return "redirect:/admin/t9/dataElements";

    }

    @RequestMapping(value = "/admin/t9/dataElements/autoBindOptions", method = RequestMethod.POST)
    public synchronized String autoBindOptions(Principal principal, Model model) throws IOException {
        MappingResult mappingResult = MappingRestUtility.getMappingResult();
        Map<DataElement, Set<Option>> mapped = mappingResult.getMapped();
        for (DataElement dataElement : mapped.keySet()) {
            T9DataElement t9DataElement = t9DataElementRepository.findByDhisId(dataElement.getId());
            if (t9DataElement != null) {

                Set<Option> options = mapped.get(dataElement);
                if (!GeneralUtility.isEmpty(options)) {
                    List<DiagnosisOption> newDiagnosisOptionList = new ArrayList<>();

                    for (Option option : options) {
                        DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisId(option.getId());
                        if (diagnosisOption != null) {
                            newDiagnosisOptionList.add(diagnosisOption);
                        }
                    }

                    if (!newDiagnosisOptionList.isEmpty()) {
                        t9DataElement.setOptions(newDiagnosisOptionList);
                        t9DataElement.setModificationDateTime(new Date());
                        t9DataElementRepository.save(t9DataElement);
                    }
                }

            }
        }
        return "redirect:/admin/t9/dataElements";
    }

    @RequestMapping(value = "/admin/t9/dataElements/bindOptionsFromFile", method = RequestMethod.GET)
    public String getUploadForm(Model model, Principal principal) {
        return "t9DataElement/bindOptionsFromFile";
    }

    @RequestMapping(value = "/admin/t9/dataElements/bindOptionsFromFile", method = RequestMethod.POST)
    public synchronized String upload(MultipartFile excelInput, Principal principal, Model model) throws IOException {
        if (excelInput != null && !excelInput.isEmpty()) {
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelInput.getBytes()));
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                T9DataElement t9DataElement = null;
                String dataElementId = "";
                if (row.getCell(0) != null) {
                    dataElementId = row.getCell(0).getStringCellValue();
                }

                String optionCodes = "";
                if (row.getCell(2) != null) {
                    optionCodes = row.getCell(2).getStringCellValue();
                }

                if (!GeneralUtility.isEmpty(dataElementId)) {
                    t9DataElement = t9DataElementRepository.findByDhisId(dataElementId);
                }

                if (t9DataElement != null) {
                    String[] optionCodesArray = optionCodes.split(",");
                    if (optionCodesArray.length > 0) {
                        List<DiagnosisOption> newDiagnosisOptionList = new ArrayList<>();
                        for (String optionCode : optionCodesArray) {
                            if (!GeneralUtility.isEmpty(optionCode)) {
                                optionCode=optionCode.trim();
                                DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisCode(optionCode);
                                if (diagnosisOption != null) {
                                    newDiagnosisOptionList.add(diagnosisOption);
                                }
                            }
                        }

                        if (!newDiagnosisOptionList.isEmpty()) {
                            t9DataElement.setOptions(newDiagnosisOptionList);
                            t9DataElement.setModificationDateTime(new Date());
                            t9DataElementRepository.save(t9DataElement);
                        }
                    }

                }

            }

        }

        return "redirect:/admin/t9/dataElements";

    }

}
