package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DataSetValueSearchDto;
import com.itinordic.interop.criteria.T9FormElementSearchDto;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.entity.T9FormElement;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.repo.T9FormElementRepository;
import com.itinordic.interop.service.T9FormElementService;
import com.itinordic.interop.dhis.CategoryCombo;
import com.itinordic.interop.dhis.CategoryOptionCombo;
import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.DataSet;
import com.itinordic.interop.dhis.DataSetElement;
import com.itinordic.interop.dhis.service.DataSetService;
import com.itinordic.interop.util.FileDto;
import static com.itinordic.interop.util.GeneralUtility.encloseCsvString;
import com.itinordic.interop.util.PageUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private DataSetService dataSetService;

    @RequestMapping(value = "/admin/t9/formElements", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9FormElementSearchDto searchDto) {
        Page<T9FormElement> formElementsPage = t9FormElementService.findT9FormElements(searchDto, "dataElement.dhisName", false, 13);
        model.addAttribute("formElements", formElementsPage);
        PageUtil.injectPageAspects(model, formElementsPage);
        return "t9FormElement/t9FormElements";
    }

    @RequestMapping(value = "/admin/t9/formElements/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) {
        DataSet dataSet = dataSetService.getDataSet();
        List<DataSetElement> dataSetElements = dataSet.getDataSetElements();
        for (DataSetElement dataSetElement : dataSetElements) {
            DataElement dataElement = dataSetElement.getDataElement();
            CategoryCombo categoryCombo = dataElement.getCategoryCombo();
            T9DataElement t9DataElement = t9DataElementRepository.findByDhisId(dataElement.getId());
            if (categoryCombo != null) {
                List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                    for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {
                        String categoryOptionComboId = categoryOptionCombo.getId();
                        T9FormElement t9FormElement = t9FormElementRepository.findByDataElementAndCategoryOptionComboId(t9DataElement, categoryOptionComboId);
                        if (t9FormElement == null) {
                            t9FormElement = new T9FormElement();
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

    @RequestMapping(path = "/admin/t9/formElements/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFormElements(DataSetValueSearchDto searchDto) throws IOException {
        FileDto fileDto = getFileDto(dataSetService.getDataSet());
        ByteArrayInputStream bas = new ByteArrayInputStream(fileDto.getContent().getBytes(Charset.forName("UTF-8")));
        Resource resource = new InputStreamResource(bas);
        String fileName = fileDto.getName().replaceAll("\\s+", "");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }

    @RequestMapping(value = "/admin/t9/formElements/downloadInZip", produces = "application/zip")
    public void downloadFormElementsValuesInZip(HttpServletResponse response, DataSetValueSearchDto searchDto) throws IOException {
        FileDto fileDto = getFileDto(dataSetService.getDataSet());
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"FormElements.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.putNextEntry(new ZipEntry(fileDto.getName()));
            zipOutputStream.write(fileDto.getContent().getBytes());
            zipOutputStream.closeEntry();
        }

    }

    protected FileDto getFileDto(DataSet dataSet) {

        FileDto fileDto = new FileDto();
        String fileName = "DataElementsWithCategoryOptions" + ".csv";
        fileDto.setName(fileName);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        String[] fields = {"data_element_id", "data_element_code", "data_element_name", "categories", "category_option_combo_id", "category_option_combo_name"};
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                printWriter.print(",");
            }
            printWriter.print(encloseCsvString(fields[i]));

        }

        for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
            DataElement dataElement = dataSetElement.getDataElement();
            CategoryCombo categoryCombo = dataElement.getCategoryCombo();
            if (categoryCombo != null) {
                List<CategoryOptionCombo> categoryOptionCombos = categoryCombo.getCategoryOptionCombos();
                if (categoryOptionCombos != null && !categoryOptionCombos.isEmpty()) {
                    for (CategoryOptionCombo categoryOptionCombo : categoryOptionCombos) {
                        printWriter.println();
                        String[] data = {
                            dataElement.getId(),
                            dataElement.getCode(),
                            dataElement.getName(),
                            categoryCombo.getCategoriesAsString(),
                            categoryOptionCombo.getId(),
                            categoryOptionCombo.getName()
                        };

                        for (int i = 0; i < fields.length; i++) {
                            if (i > 0) {
                                printWriter.print(",");
                            }

                            printWriter.print(encloseCsvString(data[i]));

                        }
                    }
                }
            }
        }

        printWriter.flush();
        fileDto.setContent(stringWriter.toString());
        return fileDto;

    }

}
