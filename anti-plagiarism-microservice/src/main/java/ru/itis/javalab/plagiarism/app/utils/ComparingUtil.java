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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ComparingUtil {

    private final Path projectStoragePath;

    @Autowired
    public ComparingUtil(FileStorageProperties fileStorageProperties) {
        projectStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("project"));
    }

    public Map<String, String> findSimilarityMap(String studentFirstName, String studentLastName, Long themeId) {

        Path rootDir = this.projectStoragePath.resolve(themeId.toString());
        String mainProjectName = studentFirstName.concat("_").concat(studentLastName).concat("_").concat(themeId.toString());
        Map<String, String> result = new HashMap<>();
        try {
            JPlagOptions jPlagOptions = new JPlagOptions(rootDir.toString(), LanguageOption.JAVA_1_9);
            JPlag jPlag = new JPlag(jPlagOptions);

            JPlagResult jPlagResult = jPlag.run();
            List<JPlagComparison> comparisons = jPlagResult.getComparisons();
            comparisons.forEach(jPlagComparison -> {
                if (jPlagComparison.firstSubmission.toString().equals(mainProjectName)){
                    result.put(jPlagComparison.secondSubmission.toString(), String.valueOf(jPlagComparison.roundedPercent()));
                }
                if (jPlagComparison.secondSubmission.toString().equals(mainProjectName)){
                    result.put(jPlagComparison.firstSubmission.toString(), String.valueOf(jPlagComparison.roundedPercent()));
                }
            });
        } catch (ExitException e) {
            throw new ComparingUtilException("JPlag problems...", e);
        }
        return result;
    }

//    public List<JPlagComparison> compareList() {
//        JPlagOptions options = new JPlagOptions(rootDir, LanguageOption.JAVA_1_9);
//        options.setBaseCodeSubmissionName(themeName);
//        JPlag jplag;
//        JPlagResult result;
//        List<JPlagComparison> comparisons = null;
//        try {
//            jplag = new JPlag(options);
//            result = jplag.run();
//        } catch (ExitException e) {
//            throw new IllegalStateException(e);
//        }
//        if (result != null) {
//            comparisons = result.getComparisons();
//        }
//        return comparisons;
//    }

}
