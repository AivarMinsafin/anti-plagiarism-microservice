package ru.itis.javalab.plagiarism.app.utils;

import jplag.ExitException;
import jplag.JPlag;
import jplag.JPlagComparison;
import jplag.JPlagResult;
import jplag.options.JPlagOptions;
import jplag.options.LanguageOption;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalab.plagiarism.app.properties.FileStorageProperties;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ComparingUtil {

    private final Path projectStoragePath;

    @Autowired
    public ComparingUtil(FileStorageProperties fileStorageProperties) {
        projectStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("project"));
    }

    public List<JPlagComparison> compareList(String BaseStudentProjectName) {
        JPlagOptions options = new JPlagOptions(projectStoragePath.toAbsolutePath().toString(), LanguageOption.JAVA_1_9);
        options.setBaseCodeSubmissionName(BaseStudentProjectName);
        JPlag jplag;
        JPlagResult result;
        List<JPlagComparison> comparisons = null;
        try {
            jplag = new JPlag(options);
            result = jplag.run();
        } catch (ExitException e) {
            throw new IllegalStateException(e);
        }
        if (result != null) {
            comparisons = result.getComparisons();
        }
        return comparisons;
    }

}
