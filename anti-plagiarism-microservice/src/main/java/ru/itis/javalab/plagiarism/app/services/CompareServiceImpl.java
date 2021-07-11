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
import java.util.stream.Collectors;

@Service
public class CompareServiceImpl implements CompareService {

    @Autowired
    private StudentRepository studentRep;

    @Autowired
    private TaskRepository taskRep;

    @Autowired
    private ComparingUtil comparingUtil;

    @Override
    public Map<String, String> getSimilarityForStudentWithIdAndThemeId(Long studentId, Long themeId) {
        Student student = studentRep.findById(studentId).orElseThrow(NotFoundException::new);
        Task task = taskRep.findByThemeIdAndStudent_Id(themeId, studentId).orElseThrow(NotFoundException::new);
        String[] projectPathSplit = task.getProjectPath().split("/");
        StringBuilder projectPathTemplate = new StringBuilder();
        for (int i = 0; i < projectPathSplit.length - 2; i++) {
            projectPathTemplate.append(projectPathSplit[i]);
        }
        String projectName = projectPathSplit[projectPathSplit.length - 1];
        Map<String, Task> tasksMap = taskRep.findAllByThemeNameMap(task.getThemeName());
        List<JPlagComparison> comparisons = comparingUtil.compareList(projectName);
        Map<String, String> map = new HashMap<>();
        boolean flag = true;
        for (JPlagComparison c : comparisons) {
            if (flag) {
                flag = false;
                map.put(student.getId() + " " + student.getFirstName() + " " + student.getLastName() + " " + student.getEmail(), "");
            }
            if (c.firstSubmission.name.equals(projectName)) {
                Student student1 = tasksMap.get(projectPathTemplate + c.secondSubmission.name).getStudent();
                map.put(student1.getId() + " " +  student1.getFirstName()  + " " + student1.getLastName() + " " + student1.getEmail(),
                        Float.toString(c.roundedPercent()));
            }
        }
        return map;
    }
}
