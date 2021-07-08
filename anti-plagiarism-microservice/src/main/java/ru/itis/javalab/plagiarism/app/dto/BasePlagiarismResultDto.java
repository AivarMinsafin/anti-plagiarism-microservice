package ru.itis.javalab.plagiarism.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasePlagiarismResultDto {

    private Long studentId;
    private Long plagiarismStudentId;
    private Integer plagiarismPercentage;
    private Long themeId;

}
