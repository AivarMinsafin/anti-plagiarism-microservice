package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.form.CodePlagiarismGetResultForm;
import ru.itis.javalab.plagiarism.app.services.CodePlagiarismCheckService;

@RestController
public class CodePlagiarismCheckController {

    @Autowired
    private CodePlagiarismCheckService codePlagiarismCheckService;

    @GetMapping("/api/plagiarism/check/task")
    public ResponseEntity<PlagiarismResultDto> getResultOfTaskCheck(@RequestBody CodePlagiarismGetResultForm form) {
        return ResponseEntity.ok(codePlagiarismCheckService.getSimilarityForStudentWithIdAndThemeId(form.getStudentId(), form.getThemeId()));
    }

}
