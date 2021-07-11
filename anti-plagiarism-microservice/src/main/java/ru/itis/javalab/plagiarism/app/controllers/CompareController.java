package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.form.GetResultForm;
import ru.itis.javalab.plagiarism.app.services.CompareService;

import java.util.Map;

@RestController
public class CompareController {

    @Autowired
    private CompareService compareService;

    @GetMapping("/api/plagiarism/getResult")
    public ResponseEntity<PlagiarismResultDto> getResult(@RequestBody GetResultForm form) {
        return ResponseEntity.ok(compareService.getSimilarityForStudentWithIdAndThemeId(form.getStudentId(), form.getThemeId()));
    }
}
