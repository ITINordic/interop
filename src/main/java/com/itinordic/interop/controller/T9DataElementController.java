package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DataSetValueSearchDto;
import com.itinordic.interop.criteria.T9DataElementSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.entity.T9DataElement;
import com.itinordic.interop.exceptions.NonAjaxNotFoundException;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.repo.T9DataElementRepository;
import com.itinordic.interop.service.T9DataElementService;
import com.itinordic.interop.dhis.DataElement;
import com.itinordic.interop.dhis.DataSet;
import com.itinordic.interop.dhis.DataSetElement;
import com.itinordic.interop.util.GeneralUtility;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import static com.itinordic.interop.util.GeneralUtility.parseIdUuid;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class T9DataElementController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private T9DataElementRepository t9DataElementRepository;
    @Autowired
    private DiagnosisOptionRepository diagnosisOptionRepository;
    @Autowired
    private T9DataElementService t9DataElementService;
    @Autowired
    private DataSetService dataSetService;

    @RequestMapping(value = "/admin/t9/dataElements", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9DataElementSearchDto searchDto) {
        Page<T9DataElement> dataElementsPage = t9DataElementService.findT9DataElements(searchDto, "modificationDateTime", true, 10);
        model.addAttribute("dataElements", dataElementsPage);
        PageUtil.injectPageAspects(model, dataElementsPage);
        return "t9DataElement/t9DataElements";
    }

    @RequestMapping(value = "/admin/t9/dataElements/sync", method = RequestMethod.POST)
    public synchronized String sync(Principal principal, Model model) throws IOException {
        DataSet dataSet = dataSetService.getDataSet();
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

    @RequestMapping(value = "/admin/t9/dataElements/{dataElementId}/edit", method = RequestMethod.GET)
    public String initEditT9DataElement(@PathVariable("dataElementId") String dataElementId, Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9DataElementSearchDto searchDto) {
        T9DataElement dataElement = t9DataElementRepository.getOne(parseIdUuid(dataElementId));
        model.addAttribute("dataElement", dataElement);
        model.addAttribute("fixedDataElement", dataElement);
        return "t9DataElement/editOptionsForm";
    }

    @RequestMapping(value = "/admin/t9/dataElements/{dataElementId}/edit", method = RequestMethod.POST)
    public String processEditT9DataElement(@PathVariable("dataElementId") String dataElementId, @Valid @ModelAttribute("dataElement") T9DataElement dataElement, BindingResult result, Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9DataElementSearchDto searchDto) throws IOException {
        T9DataElement $dataElement = t9DataElementRepository.getOne(parseIdUuid(dataElementId));
        if ($dataElement == null) {
            throw new NonAjaxNotFoundException();
        }

        if (!dataElement.hasOptions()) {
            result.rejectValue("options", "noOptionsCode", "Please select one or more options");
        }

        dataElement.setId(parseIdUuid(dataElementId));

        if (result.hasErrors()) {
            model.addAttribute("fixedDataElement", $dataElement);
            return "t9DataElement/editOptionsForm";
        }
        $dataElement.copy(dataElement);
        $dataElement.setUpdatorUserName(principal.getName());
        $dataElement.setModificationDateTime(new Date());
        t9DataElementRepository.save($dataElement);

        String urlSuffix = "?q=" + searchDto.getQ() + "&page=" + searchDto.getPageNumber();
        if (searchDto.srcEquals("unmapped")) {
            return "redirect:/admin/t9/dataElements/unmapped" + urlSuffix;
        } else {
            return "redirect:/admin/t9/dataElements" + urlSuffix;
        }

    }

    @RequestMapping(value = "/admin/t9/dataElements/autoBindOptions", method = RequestMethod.POST)
    public synchronized String autoBindOptions(Principal principal, Model model) throws IOException {
        t9DataElementService.autoBindOptions();
        return "redirect:/admin/t9/dataElements";
    }

    @RequestMapping(value = "/admin/t9/dataElements/unmapped", method = RequestMethod.GET)
    public String unmappedDataElements(Principal principal, Model model, @ModelAttribute("defaultSearchDto") T9DataElementSearchDto searchDto) {
        searchDto.setNoOptions(true);
        Page<T9DataElement> dataElementsPage = t9DataElementService.findT9DataElements(searchDto, "modificationDateTime", true, 10);
        model.addAttribute("dataElements", dataElementsPage);
        PageUtil.injectPageAspects(model, dataElementsPage);
        return "t9DataElement/t9UnmappedDataElements";
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

                if (t9DataElement == null) {
                    logger.info("DataElement with id %s not found", dataElementId);
                    continue;
                }

                String[] optionCodesArray = optionCodes.split(",");
                if (optionCodesArray.length < 1 && !GeneralUtility.isEmpty(optionCodes)) {
                    optionCodesArray = new String[]{optionCodes.trim()};
                }

                if (optionCodesArray.length < 1) {
                    logger.info("No any option code for dataElement with id %s", dataElementId);
                    continue;
                }

                List<DiagnosisOption> newDiagnosisOptionList = new ArrayList<>();
                for (String optionCode : optionCodesArray) {
                    if (!GeneralUtility.isEmpty(optionCode)) {
                        optionCode = optionCode.trim();
                        DiagnosisOption diagnosisOption = diagnosisOptionRepository.findByDhisCode(optionCode);
                        if (diagnosisOption != null) {
                            newDiagnosisOptionList.add(diagnosisOption);
                        } else {
                            logger.info("DiagnosisOption with code %s not found", optionCode);
                        }
                    }
                }

                if (newDiagnosisOptionList.isEmpty()) {
                    logger.info("Empty diagnosis option list for dataElement with id %s", dataElementId);
                    continue;
                }

                //Merge options 
                List<DiagnosisOption> oldDiagnosisOptionList = t9DataElement.getOptions();
                if (!GeneralUtility.isEmpty(oldDiagnosisOptionList)) {
                    Set<DiagnosisOption> mergedOptions = new HashSet<>(oldDiagnosisOptionList);
                    mergedOptions.addAll(newDiagnosisOptionList);
                    newDiagnosisOptionList = new ArrayList<>(mergedOptions);
                }

                t9DataElement.setOptions(newDiagnosisOptionList);
                t9DataElement.setModificationDateTime(new Date());
                t9DataElement.setUpdatorUserName(principal.getName());
                t9DataElementRepository.save(t9DataElement);

            }

        } else {
            return "t9DataElement/bindOptionsFromFile";
        }

        return "redirect:/admin/t9/dataElements";

    }

    @RequestMapping(path = "/admin/t9/dataElements/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadDataElements(DataSetValueSearchDto searchDto) throws IOException {
        FileDto fileDto = getFileDto(dataSetService.getDataSet());
        ByteArrayInputStream bas = new ByteArrayInputStream(fileDto.getContent().getBytes(Charset.forName("UTF-8")));
        Resource resource = new InputStreamResource(bas);
        String fileName = fileDto.getName().replaceAll("\\s+", "");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }

    @RequestMapping(value = "/admin/t9/dataElements/downloadInZip", produces = "application/zip")
    public void downloadDataElementsInZip(HttpServletResponse response, DataSetValueSearchDto searchDto) throws IOException {
        FileDto fileDto = getFileDto(dataSetService.getDataSet());
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"DataElements.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.putNextEntry(new ZipEntry(fileDto.getName()));
            zipOutputStream.write(fileDto.getContent().getBytes());
            zipOutputStream.closeEntry();
        }

    }

    @RequestMapping(path = "/admin/t9/dataElements/withOptions/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadDataElementsWithOptions(DataSetValueSearchDto searchDto) throws IOException {
        Workbook workbook = createDataElementsWorkbook();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        workbook.write(bao);
        ByteArrayInputStream bas = new ByteArrayInputStream(bao.toByteArray());
        Resource resource = new InputStreamResource(bas);
        String fileName = "DataElementsWithOptions.xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType("application/xlsx"))
                .body(resource);
    }

    @RequestMapping(value = "/admin/t9/dataElements/withOptions/downloadInZip", produces = "application/zip")
    public void downloadDataElementsWithOptionsInZip(HttpServletResponse response, DataSetValueSearchDto searchDto) throws IOException {
        Workbook workbook = createDataElementsWorkbook();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        workbook.write(bao);
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"DataElementsWithOptions.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.putNextEntry(new ZipEntry("DataElementsWithOptions.xlsx"));
            zipOutputStream.write(bao.toByteArray());
            zipOutputStream.closeEntry();
        }

    }

    private FileDto getFileDto(DataSet dataSet) {

        FileDto fileDto = new FileDto();
        String fileName = "DataElements" + ".csv";
        fileDto.setName(fileName);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        String[] fields = {"data_element_id", "data_element_code", "data_element_name"};
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                printWriter.print(",");
            }
            printWriter.print(encloseCsvString(fields[i]));

        }

        for (DataSetElement dataSetElement : dataSet.getDataSetElements()) {
            DataElement dataElement = dataSetElement.getDataElement();
            printWriter.println();
            String[] data = {
                dataElement.getId(),
                dataElement.getCode(),
                dataElement.getName()
            };

            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    printWriter.print(",");
                }

                printWriter.print(encloseCsvString(data[i]));

            }
        }

        printWriter.flush();
        fileDto.setContent(stringWriter.toString());
        return fileDto;

    }

    private Workbook createDataElementsWorkbook() {

        List<T9DataElement> dataElements = t9DataElementRepository.findAll();
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Data Elements");
        Row header = sheet.createRow(0);

        String[] headers = {"data_element_id", "data_element_name", "linked_options"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (int i = 0; i < dataElements.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T9DataElement dataElement = dataElements.get(i);

            String[] data = {dataElement.getDhisId(), dataElement.getDhisName(), dataElement.getOptionsAsCodes()};
            for (int j = 0; j < data.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data[j]);
            }
        }

        return workbook;

    }

}
