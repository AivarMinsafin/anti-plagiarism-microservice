package ru.itis.javalab.plagiarism.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.plagiarism.app.models.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByThemeIdAndStudent_Id(Long themeId, Long studentId);
}
