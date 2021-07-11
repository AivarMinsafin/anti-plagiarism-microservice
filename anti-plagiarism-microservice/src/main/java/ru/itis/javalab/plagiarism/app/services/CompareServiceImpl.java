package ru.itis.javalab.plagiarism.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.SimilarStudentDto;
import ru.itis.javalab.plagiarism.app.dto.SuspectStudentDto;
import ru.itis.javalab.plagiarism.app.exceptions.NotFoundException;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.models.Task;
import ru.itis.javalab.plagiarism.app.repositories.StudentRepository;
import ru.itis.javalab.plagiarism.app.repositories.TaskRepository;
import ru.itis.javalab.plagiarism.app.utils.ComparingUtil;
import ru.itis.javalab.plagiarism.app.utils.ComparingUtilImpl;

import java.util.ArrayList;
import java.util.Map;

@Service
public class CompareServiceImpl implements CompareService {

    @Autowired
    private StudentRepository studentRep;

    @Autowired
    private ComparingUtil comparingUtil;

    @Autowired
    private TaskRepository taskRep;

    @Override
    public PlagiarismResultDto getSimilarityForStudentWithIdAndThemeId(Long studentId, Long themeId) {
        Student student = studentRep.findByStudentId(studentId).orElseThrow(NotFoundException::new);
        Task task = taskRep.findByThemeIdAndStudent_Id(themeId, student.getId()).orElseThrow(NotFoundException::new);
        String projectDir = task.getProjectPath();
        PlagiarismResultDto plagiarismResultDto = PlagiarismResultDto.builder()
                .suspect(
                        SuspectStudentDto.builder()
                                .firstName(student.getFirstName())
                                .lastName(student.getLastName())
                                .email(student.getEmail())
                                .archivePath(task.getArchivePath())
                                .studentId(student.getStudentId())
                                .build()
                )
                .similarStudents(new ArrayList<>())
                .build();
        Map<String, String> similarityMap = comparingUtil.findSimilarityMap(student.getFirstName(), student.getLastName(), themeId);
        similarityMap.forEach((key, value) -> {
            Task similarTask = taskRep.findByProjectPathContaining(key).orElseThrow(NotFoundException::new);
            Student similarStudent = studentRep.findByTasksContains(similarTask).orElseThrow(NotFoundException::new);
            plagiarismResultDto.getSimilarStudents().add(
                    SimilarStudentDto.builder()
                            .studentId(similarStudent.getId())
                            .firstName(similarStudent.getFirstName())
                            .lastName(similarStudent.getLastName())
                            .email(similarStudent.getEmail())
                            .archivePath(similarTask.getArchivePath())
                            .similarityPercent(value)
                            .build()
            );
        });
        return plagiarismResultDto;
    }
}
