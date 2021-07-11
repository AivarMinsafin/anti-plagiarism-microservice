package ru.itis.javalab.plagiarism.app.services;

import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;

public interface CompareService {
    PlagiarismResultDto getSimilarityForStudentWithIdAndThemeId(Long studentId, Long themeId);
}
