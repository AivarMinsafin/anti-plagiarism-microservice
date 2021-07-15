package ru.itis.javalab.plagiarism.app.utils.plagiarism.report;

import java.util.Map;

public interface ReportPlagiarismCheckUtil {
    Map<String, String> findSimilarityMap(Long courseId, String studentFirstName, String studentLastName, Long studentId);
}
