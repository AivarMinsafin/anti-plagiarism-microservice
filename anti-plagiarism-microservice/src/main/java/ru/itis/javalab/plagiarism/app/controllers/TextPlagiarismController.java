package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.BasePlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.form.BaseTextPlagiarismCheckForm;

import java.util.Random;

@RestController
public class TextPlagiarismController {

    @PostMapping("/api/plagiarism/base-text-check")
    public ResponseEntity<BasePlagiarismResultDto> checkTextPlagiarism(@RequestBody BaseTextPlagiarismCheckForm form) {
        return ResponseEntity.ok(BasePlagiarismResultDto.builder()
        .studentId(form.getStudentId())
        .plagiarismPercentage(new Random(100).nextInt())
        .build());
    }

}
