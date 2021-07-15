package ru.itis.javalab.plagiarism.app.utils.storage.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageUtil {
    String storeArchive(MultipartFile file, String path, String fileName, String fileExt);
    String storeReport(MultipartFile file, String path, String fileName, String fileExt);
    Resource loadFileAsResource(String path, String type);
    void extractArchive(String fileZip, String destDir);
}
