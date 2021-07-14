package ru.itis.javalab.plagiarism.app.services;

import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;

public interface ReportPlagiarismCheckService {
    PlagiarismResultDto getSimilarityForStudentWithIdAndCourseId(Long studentId, Long courseId);
}
