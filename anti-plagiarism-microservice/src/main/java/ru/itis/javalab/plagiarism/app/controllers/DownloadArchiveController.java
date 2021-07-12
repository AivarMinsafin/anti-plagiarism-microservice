package ru.itis.javalab.plagiarism.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.plagiarism.app.dto.form.DownloadArchiveForm;
import ru.itis.javalab.plagiarism.app.services.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class DownloadArchiveController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/api/plagiarism/downloadTask")
    public ResponseEntity<Resource> downloadArchive(@RequestBody DownloadArchiveForm downloadArchiveForm, HttpServletRequest request) {
        Resource resource = taskService.downloadArchive(downloadArchiveForm);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
