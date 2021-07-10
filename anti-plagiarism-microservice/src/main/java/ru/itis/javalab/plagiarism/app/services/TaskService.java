package ru.itis.javalab.plagiarism.app.services;

import ru.itis.javalab.plagiarism.app.dto.form.AddTaskForm;

public interface TaskService {
    void addTask(AddTaskForm form);
}
