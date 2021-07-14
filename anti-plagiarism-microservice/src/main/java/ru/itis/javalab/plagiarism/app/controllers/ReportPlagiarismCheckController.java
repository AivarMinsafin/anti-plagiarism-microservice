package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.form.CodePlagiarismGetResultForm;
import ru.itis.javalab.plagiarism.app.dto.form.ReportPlagiarismGetResultForm;
import ru.itis.javalab.plagiarism.app.services.ReportPlagiarismCheckService;

@RestController
public class ReportPlagiarismCheckController {

    @Autowired
    private ReportPlagiarismCheckService reportPlagiarismCheckService;

    @GetMapping("/api/plagiarism/check/reports")
    public ResponseEntity<PlagiarismResultDto> getResultOfReportCheck(@RequestBody ReportPlagiarismGetResultForm form) {
        return ResponseEntity.ok(reportPlagiarismCheckService.getSimilarityForStudentWithIdAndCourseId(
                form.getStudentId(), form.getCourseId()));
    }

}
