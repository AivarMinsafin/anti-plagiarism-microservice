package ru.itis.javalab.plagiarism.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.plagiarism.app.models.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByThemeIdAndStudent_Id(Long themeId, Long studentId);
    Optional<Task> findByProjectPathContaining(String projectPath);
}
