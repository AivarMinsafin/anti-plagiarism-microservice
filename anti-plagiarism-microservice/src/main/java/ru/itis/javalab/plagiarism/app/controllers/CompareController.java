package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.form.GetResultForm;
import ru.itis.javalab.plagiarism.app.services.CompareService;

import java.util.Map;

@RestController
public class CompareController {

    @Autowired
    private CompareService compareService;

    @PostMapping("/api/plagiarism/getResult")
    public ResponseEntity<Map<String, String>> getResult(@RequestBody GetResultForm form) {
        Map<String, String> map = compareService.compare(form.getThemeName(), form.getRootDir());
        return ResponseEntity.ok(map);
    }
}
