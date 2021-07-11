package ru.itis.javalab.plagiarism.app.services;

import jplag.JPlagComparison;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface CompareService {
    Map<String, String> getSimilarityForStudentWithIdAndThemeId(String themeName, String rootDir);
}
