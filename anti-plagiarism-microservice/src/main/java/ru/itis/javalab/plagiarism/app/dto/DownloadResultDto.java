package ru.itis.javalab.plagiarism.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DownloadResultDto {
    private Resource resource;
    private String contentType;
}
