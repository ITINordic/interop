package com.itinordic.interop.controller;

import com.itinordic.interop.criteria.DataSetValueSearchDto;
import com.itinordic.interop.criteria.DiagnosisOptionSearchDto;
import com.itinordic.interop.entity.DiagnosisOption;
import com.itinordic.interop.repo.DiagnosisOptionRepository;
import com.itinordic.interop.service.DiagnosisOptionService;
import com.itinordic.interop.dhis.Option;
import com.itinordic.interop.dhis.OptionSet;
import com.itinordic.interop.dhis.service.OptionSetService;
import com.itinordic.interop.util.FileDto;
import static com.itinordic.interop.util.GeneralUtility.encloseCsvString;
import com.itinordic.interop.util.PageUtil;
import com.itinordic.interop.util.Select2Object;
import com.itinordic.interop.util.Select2ObjectBag;
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
    @Autowired
    private OptionSetService optionSetService;

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
        OptionSet optionSet = optionSetService.getOptionSet();
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
            bag.add(new Select2Object(option.getId().toString(), option.getDhisName() + " ("+option.getLinkStatus()+")"));
        }
        return bag;
    }
    
    @RequestMapping(path = "/admin/diagnosis/options/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadOptions(DataSetValueSearchDto searchDto) throws IOException {
        FileDto fileDto = getFileDto(optionSetService.getOptionSet());
        ByteArrayInputStream bas = new ByteArrayInputStream(fileDto.getContent().getBytes(Charset.forName("UTF-8")));
        Resource resource = new InputStreamResource(bas);
        String fileName = fileDto.getName().replaceAll("\\s+", "");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }

    @RequestMapping(value = "/admin/diagnosis/options/downloadInZip", produces = "application/zip")
    public void downloadOptionsInZip(HttpServletResponse response,DataSetValueSearchDto searchDto) throws IOException {
        FileDto fileDto = getFileDto(optionSetService.getOptionSet());
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"Options.zip\"");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.putNextEntry(new ZipEntry(fileDto.getName()));
            zipOutputStream.write(fileDto.getContent().getBytes());
            zipOutputStream.closeEntry();
        }

    }

    protected FileDto getFileDto(OptionSet optionSet) {

        FileDto fileDto = new FileDto();
        String fileName = "Options" + ".csv";
        fileDto.setName(fileName);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        String[] fields = {"option_id", "option_code", "option_name"};
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                printWriter.print(",");
            }
            printWriter.print(encloseCsvString(fields[i]));

        }

        for (Option option : optionSet.getOptions()) {
            printWriter.println();
            String[] data = {
                option.getId(),
                option.getCode(),
                option.getName()
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

}
