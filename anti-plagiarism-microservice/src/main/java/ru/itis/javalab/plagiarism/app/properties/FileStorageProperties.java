package ru.itis.javalab.plagiarism.app.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private Map<String, String> uploadDir;

    public Map<String, String> getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(Map<String, String> uploadDir) {
        this.uploadDir = uploadDir;
    }
}
