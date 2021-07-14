package ru.itis.javalab.plagiarism.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.plagiarism.app.dto.PlagiarismResultDto;
import ru.itis.javalab.plagiarism.app.dto.SimilarStudentDto;
import ru.itis.javalab.plagiarism.app.dto.SuspectStudentDto;
import ru.itis.javalab.plagiarism.app.exceptions.NotFoundException;
import ru.itis.javalab.plagiarism.app.models.Report;
import ru.itis.javalab.plagiarism.app.models.Student;
import ru.itis.javalab.plagiarism.app.repositories.ReportRepository;
import ru.itis.javalab.plagiarism.app.repositories.StudentRepository;
import ru.itis.javalab.plagiarism.app.utils.ReportsPlagiarism.ReportPlagiarismCheckUtil;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ReportPlagiarismCheckServiceImpl implements ReportPlagiarismCheckService {

    @Autowired
    private StudentRepository studentRep;

    @Autowired
    private ReportPlagiarismCheckUtil reportPlagiarismCheckUtil;

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public PlagiarismResultDto getSimilarityForStudentWithIdAndCourseId(Long studentId, Long courseId) {
        Student student = studentRep.findByStudentId(studentId).orElseThrow(NotFoundException::new);
        Report report = reportRepository.findByCourseIdAndStudent_Id(courseId, studentId).orElseThrow(NotFoundException::new);
        PlagiarismResultDto plagiarismResultDto = PlagiarismResultDto.builder()
                .suspect(
                        SuspectStudentDto.builder()
                                .firstName(student.getFirstName())
                                .lastName(student.getLastName())
                                .email(student.getEmail())
                                .path(report.getReportPath())
                                .studentId(student.getStudentId())
                                .build()
                )
                .similarStudents(new ArrayList<>())
                .build();
        Map<String, String> similarityMap = reportPlagiarismCheckUtil.findSimilarityMap(report.getCourseId(),
                student.getFirstName(), student.getLastName(), student.getStudentId());
        similarityMap.forEach((key, value) -> {
            Report similarReport = reportRepository.findByReportPathContaining(key).orElseThrow(NotFoundException::new);
            Student similarStudent = studentRep.findByReportsContains(similarReport).orElseThrow(NotFoundException::new);
            plagiarismResultDto.getSimilarStudents().add(
                    SimilarStudentDto.builder()
                    .studentId(similarStudent.getStudentId())
                    .firstName(similarStudent.getFirstName())
                    .lastName(similarStudent.getLastName())
                    .email(similarStudent.getEmail())
                    .path(similarReport.getReportPath())
                    .similarityPercent(value)
                    .build()
            );
        });
        return plagiarismResultDto;
    }
}
