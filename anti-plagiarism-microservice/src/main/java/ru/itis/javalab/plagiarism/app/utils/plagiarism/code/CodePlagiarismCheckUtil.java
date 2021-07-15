package ru.itis.javalab.plagiarism.app.utils.plagiarism.code;

import java.util.Map;

public interface CodePlagiarismCheckUtil {
    Map<String, String> findSimilarityMap(String studentFirstName, String studentLastName, Long themeId, Long studentId);
}
