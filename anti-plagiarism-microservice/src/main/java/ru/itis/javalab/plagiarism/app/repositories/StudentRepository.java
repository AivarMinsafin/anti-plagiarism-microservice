package ru.itis.javalab.plagiarism.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.plagiarism.app.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
