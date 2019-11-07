package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DiagnosisFormSearchDto;
import com.itinordic.interop.dao.DataSetValueDao;
import com.itinordic.interop.util.DataSetValueElement;
import com.itinordic.interop.util.FileDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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
public class T9DataSetValueController {

    @Autowired
    private DataSetValueDao dataSetValueDao;

    @RequestMapping(value = "/admin/report/t9DataSetValues", method = RequestMethod.GET)
    public String getAll(Principal principal, Model model, @ModelAttribute("defaultSearchDto") DiagnosisFormSearchDto searchDto) {
        model.addAttribute("dataSetValues", dataSetValueDao.findDataSetValueElements());
        return "t9DataSetValue/t9DataSetValues";
    }

    @RequestMapping(path = "/admin/report/t9DataSetValues/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadDataSetValues() throws IOException {
        FileDto fileDto = getFileDto(dataSetValueDao.findDataSetValueElements());
        ByteArrayInputStream bas = new ByteArrayInputStream(fileDto.getContent().getBytes(Charset.forName("UTF-8")));
        Resource resource = new InputStreamResource(bas);
        String fileName = fileDto.getName().replaceAll("\\s+", "");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }

    @RequestMapping(value = "/admin/report/t9DataSetValues/downloadInZip", produces = "application/zip")
    public void downloadDataSetValuesInZip(HttpServletResponse response) throws IOException {
        FileDto fileDto = getFileDto(dataSetValueDao.findDataSetValueElements());
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"DataSetValues.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.putNextEntry(new ZipEntry(fileDto.getName()));
            zipOutputStream.write(fileDto.getContent().getBytes());
            zipOutputStream.closeEntry();
        }

    }

    protected FileDto getFileDto(List<DataSetValueElement> dataSetValueElements) {

        FileDto fileDto = new FileDto();
        String fileName = "DataSetValues" + ".csv";
        fileDto.setName(fileName);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        String[] fields = {"dataelement", "period", "orgunit", "categoryoptioncombo", "attributeoptioncombo", "value"};
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                printWriter.print(",");
            }
            printWriter.print(enclose(fields[i]));

        }
        

        for (DataSetValueElement dataSetValueElement : dataSetValueElements) {
            printWriter.println();
            String[] data = {
                dataSetValueElement.getFormElement().getDataElement().getDhisId(),
                dataSetValueElement.getEventPeriod(),
                dataSetValueElement.getOrganizationUnit().getDhisId(),
                dataSetValueElement.getFormElement().getCategoryOptionComboId(),
                dataSetValueElement.getFormElement().getCategoryOptionComboId(),
                dataSetValueElement.getCount().toString()
            };

            for (int i = 0; i < fields.length; i++) {
                if (i > 0) {
                    printWriter.print(",");
                }

                printWriter.print(enclose(data[i]));

            }
        }

        printWriter.flush();
        fileDto.setContent(stringWriter.toString());
        return fileDto;

    }

    private String enclose(String string) {
        return "" + string + "";
    }

}
