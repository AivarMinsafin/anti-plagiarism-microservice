package ru.itis.javalab.plagiarism.app.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseTextPlagiarismCheckForm {

    private Long studentId;
    private String textForChecking;
}
