package ru.itis.javalab.plagiarism.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.plagiarism.app.models.Report;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.models.Task;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByCourseIdAndStudent_Id(Long courseId, Long studentId);
    Optional<Report> findByReportPathContaining(String projectPath);
}
