package ru.itis.javalab.plagiarism.app.services;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.javalab.plagiarism.app.dto.form.AddTaskForm;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.models.Task;
import ru.itis.javalab.plagiarism.app.repositories.StudentRepository;
import ru.itis.javalab.plagiarism.app.repositories.TaskRepository;
import ru.itis.javalab.plagiarism.app.utils.FileStorageUtil;
import ru.itis.javalab.plagiarism.app.utils.FileStorageUtilImpl;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Override
    public void addTask(AddTaskForm form) {
        Student student = studentRepository.findByStudentId(form.getStudentId()).orElse(null);
        if (student == null) {
            student = Student.builder()
                    .email(form.getEmail())
                    .studentId(form.getStudentId())
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .tasks(new ArrayList<>()).build();
        } else {
            student.setFirstName(form.getFirstName());
            student.setLastName(form.getLastName());
            student.setEmail(form.getEmail());
        }
        Task task = taskRepository.findByThemeIdAndStudent_Id(form.getThemeId(), form.getStudentId()).orElse(null);
        if (task != null) {
            taskRepository.delete(task);
        }
        PathPair pathPair = storeArchive(form.getArchive(), form.getFirstName(), form.getLastName(), form.getThemeId());
        task = Task.builder()
                .archivePath(pathPair.getArchivePath())
                .projectPath(pathPair.getProjectPath())
                .localDateTime(LocalDateTime.now())
                .themeId(form.getThemeId())
                .themeName(form.getThemeName())
                .student(student)
                .build();
        studentRepository.save(student);
        taskRepository.save(task);
    }

    private PathPair storeArchive(MultipartFile file, String firstName, String lastName, Long themeId){
        String path = themeId.toString();
        String fileName = firstName+"_"+lastName+"_"+themeId;
        String fileExt = Files.getFileExtension(file.getOriginalFilename());
        fileStorageUtil.storeArchive(file, path, fileName, fileExt);
        String projectPath = Paths.get(String.valueOf(themeId)).resolve(fileName).toString();
        String archivePath = projectPath.concat("."+fileExt);
        return PathPair.builder()
                .archivePath(archivePath)
                .projectPath(projectPath)
                .build();
    }
}
