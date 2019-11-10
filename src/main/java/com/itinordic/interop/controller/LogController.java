package com.itinordic.interop.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Charles Chigoriwa
 */
@Controller
public class LogController {

    @RequestMapping(value = "/admin/logs", method = RequestMethod.GET)
    public String getLogs(Principal principal, Model model) {
        return "log/logs";
    }

    @RequestMapping(path = "/admin/log/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadLog() throws IOException {
        Resource resource = new InputStreamResource(new FileInputStream("logs/interop-logger.log"));
        String fileName = "interop-logger.log";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType("application/text"))
                .body(resource);
    }

    @RequestMapping(value = "/admin/log/downloadInZip", produces = "application/zip")
    public void downloadLogInZip(HttpServletResponse response) throws IOException {
        FileInputStream fis = new FileInputStream("logs/interop-logger.log");
        String fileName = "interop-logger.log";
        int len;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"interop-logger.zip\"");

        while ((len = fis.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            zipOutputStream.write(baos.toByteArray());
            zipOutputStream.closeEntry();
        }

    }

}
