package ru.itis.javalab.plagiarism.app.services;

import org.springframework.core.io.Resource;
import ru.itis.javalab.plagiarism.app.dto.form.AddTaskForm;
import ru.itis.javalab.plagiarism.app.dto.form.DownloadArchiveForm;

public interface TaskService {
    void addTask(AddTaskForm form);
    Resource downloadArchive(DownloadArchiveForm form);
}
