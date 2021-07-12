package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.form.AddTaskForm;
import ru.itis.javalab.plagiarism.app.services.TaskService;


@RestController
public class AddTaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(
            value = "/api/plagiarism/addTask",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> saveTask(AddTaskForm form) {
        taskService.addTask(form);
        return ResponseEntity.ok().build();
    }

}
