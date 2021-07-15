package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.form.DownloadReportForm;
import ru.itis.javalab.plagiarism.app.services.ReportService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class DownloadReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/api/plagiarism/download/report")
    public ResponseEntity<Resource> downloadReport(@RequestBody DownloadReportForm form, HttpServletRequest request) {
        Resource resource = reportService.downloadReport(form);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return ResponseEntity.notFound().build();
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
