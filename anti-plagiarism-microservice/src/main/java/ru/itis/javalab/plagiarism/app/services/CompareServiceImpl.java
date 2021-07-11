package ru.itis.javalab.plagiarism.app.services;

import jdk.dynalink.linker.ConversionComparator;
import jplag.JPlagComparison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.itis.javalab.plagiarism.app.exceptions.NotFoundException;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.models.Task;
import ru.itis.javalab.plagiarism.app.repositories.StudentRepository;
import ru.itis.javalab.plagiarism.app.repositories.TaskRepository;
import ru.itis.javalab.plagiarism.app.utils.ComparingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompareServiceImpl implements CompareService {

    @Autowired
    private StudentRepository studentRep;

    @Autowired
    private TaskRepository taskRep;

    @Override
    public Map<String, String> getSimilarityForStudentWithIdAndThemeId(Long studentId, Long themeId) {
        Student student = studentRep.findById(studentId).orElseThrow(NotFoundException::new);
        Task task = taskRep.findByThemeIdAndStudent_Id(themeId, studentId).orElseThrow(NotFoundException::new);
        String projectDir = task.getProjectPath();
//        ComparingUtil comparingUtil = new ComparingUtil(themeName, rootDir);
//        //List<JPlagComparison> comparisons = comparingUtil.compareList();
//        Map<String, String> map = new HashMap<>();
//        for (JPlagComparison c : comparingUtil.getComparisons()) {
//            if (c.firstSubmission.name.equals(themeName)) {
//                map.put(c.toString(), Float.toString(c.roundedPercent()));
//            }
//        }
//        return map;
    }
}
