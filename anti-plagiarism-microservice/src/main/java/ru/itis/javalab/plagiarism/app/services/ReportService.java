package ru.itis.javalab.plagiarism.app.services;

import org.springframework.core.io.Resource;
import ru.itis.javalab.plagiarism.app.dto.form.AddReportForm;
import ru.itis.javalab.plagiarism.app.dto.form.DownloadReportForm;

public interface ReportService {
    void addReport(AddReportForm form);
    Resource downloadReport(DownloadReportForm form);
}
