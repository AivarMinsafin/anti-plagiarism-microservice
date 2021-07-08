package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.BasePlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.form.BaseFilePlagiarismCheckForm;

import java.util.Random;

@RestController
public class FilePlagiarismController {

    @PostMapping("/api/plagiarism/base-file-check")
    public ResponseEntity<BasePlagiarismResultDto> checkFilePlagiarism(@RequestBody BaseFilePlagiarismCheckForm form){
        return ResponseEntity.ok(BasePlagiarismResultDto.builder()
                .studentId(form.getStudentId())
                .plagiarismStudentId(new Random().nextLong())
                .plagiarismPercentage(new Random(100).nextInt())
                .themeId(new Random().nextLong())
                .build());
    }

}
