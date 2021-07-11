package ru.itis.javalab.plagiarism.app.utils;

import java.util.Map;

public interface ComparingUtil {
    Map<String, String> findSimilarityMap(String studentFirstName, String studentLastName, Long themeId);
}
