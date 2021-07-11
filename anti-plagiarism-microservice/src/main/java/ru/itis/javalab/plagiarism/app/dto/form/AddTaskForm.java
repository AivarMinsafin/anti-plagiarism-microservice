package ru.itis.javalab.plagiarism.app.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddTaskForm {

    private String firstName;
    private String lastName;
    private Long studentId;
    private String email;
    private Long themeId;
    private String themeName;
    private MultipartFile archive;

}
