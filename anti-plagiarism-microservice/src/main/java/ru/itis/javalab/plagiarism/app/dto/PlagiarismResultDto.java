package ru.itis.javalab.plagiarism.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlagiarismResultDto {

    private SuspectStudentDto suspect;
    private List<SimilarStudentDto> similarStudents;

}
