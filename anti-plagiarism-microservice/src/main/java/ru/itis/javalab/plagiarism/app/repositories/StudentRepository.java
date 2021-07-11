package ru.itis.javalab.plagiarism.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.models.Task;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Long studentId);
    Optional<Student> findByTasksContains(Task task);
}
