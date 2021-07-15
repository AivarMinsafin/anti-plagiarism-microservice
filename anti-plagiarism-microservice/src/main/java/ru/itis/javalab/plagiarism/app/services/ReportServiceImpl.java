package ru.itis.javalab.plagiarism.app.services;

import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.javalab.plagiarism.app.dto.form.AddReportForm;
import ru.itis.javalab.plagiarism.app.dto.form.DownloadReportForm;
import ru.itis.javalab.plagiarism.app.exceptions.NotFoundException;
import ru.itis.javalab.plagiarism.app.models.Report;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.repositories.ReportRepository;
import ru.itis.javalab.plagiarism.app.repositories.StudentRepository;
import ru.itis.javalab.plagiarism.app.utils.storage.file.FileStorageUtil;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void addReport(AddReportForm form) {
        Student student = studentRepository.findByStudentId(form.getStudentId()).orElse(null);
        if (student == null) {
            student = Student.builder()
                    .email(form.getEmail())
                    .studentId(form.getStudentId())
                    .firstName(form.getFirstName())
                    .lastName(form.getLastName())
                    .reports(new ArrayList<>())
                    .tasks(new ArrayList<>()).build();
        } else {
            student.setFirstName(form.getFirstName());
            student.setLastName(form.getLastName());
            student.setEmail(form.getEmail());
        }
        Report report = reportRepository.findByCourseIdAndStudent_Id(form.getCourseId(),
                form.getStudentId()).orElse(null);
        if (report != null) {
            reportRepository.delete(report);
        }
        String reportPath = storeReport(form.getReport(), form.getFirstName(), form.getLastName(), form.getCourseId(), form.getStudentId());
        report = Report.builder()
                .reportPath(reportPath)
                .courseId(form.getCourseId())
                .courseName(form.getCourseName())
                .student(student)
                .localDateTime(LocalDateTime.now())
                .build();
        studentRepository.save(student);
        reportRepository.save(report);
    }

    private String storeReport(MultipartFile file, String firstName, String lastName, Long courseId, Long studentId) {
        String path = courseId.toString();
        String fileName = firstName + "_" + lastName + "_" + studentId + "_" + courseId;
        String fileExt = Files.getFileExtension(file.getOriginalFilename());
        fileStorageUtil.storeReport(file, path, fileName, fileExt);
        return Paths.get(String.valueOf(courseId)).resolve(fileName + "." + fileExt).toString();
    }

    @Override
    public Resource downloadReport(DownloadReportForm form) {
        Report report = reportRepository.findByCourseIdAndStudent_Id(form.getCourseId(),
                form.getStudentId()).orElseThrow(NotFoundException::new);
        return fileStorageUtil.loadFileAsResource(report.getReportPath(), "report");
    }
}
