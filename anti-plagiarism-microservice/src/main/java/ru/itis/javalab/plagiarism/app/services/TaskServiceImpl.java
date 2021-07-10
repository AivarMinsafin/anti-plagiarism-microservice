package ru.itis.javalab.plagiarism.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.plagiarism.app.dto.form.AddTaskForm;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.models.Task;
import ru.itis.javalab.plagiarism.app.repositories.StudentRepository;
import ru.itis.javalab.plagiarism.app.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void addTask(AddTaskForm form) {
        Student student = studentRepository.findById(form.getStudentId()).orElse(null);
        if (student == null) {
            student = Student.builder()
                    .email(form.getEmail())
                    .id(form.getStudentId())
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .tasks(new ArrayList<>()).build();
        }
        Task task = taskRepository.findByThemeIdAndStudent_Id(form.getThemeId(), form.getStudentId()).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
        }
        task = Task.builder()
                //.archivePath(form.getArchive())
                .localDateTime(LocalDateTime.now())
                .themeId(form.getThemeId())
                .themeName(form.getThemeName())
                .student(student)
                .build();
        studentRepository.save(student);
        taskRepository.save(task);
    }
}
