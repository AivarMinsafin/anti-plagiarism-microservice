package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.form.AddReportForm;
import ru.itis.javalab.plagiarism.app.services.ReportService;

@RestController
public class AddReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(
            value = "/api/plagiarism/add/report",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> saveReport(AddReportForm form) {
        reportService.addReport(form);
        return ResponseEntity.ok().build();
    }
}
