package ru.itis.javalab.plagiarism.app.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.javalab.plagiarism.app.properties.FileStorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileStorageUtil {

    private final Path archiveStoragePath;
    private final Path reportStoragePath;
    private final Path rootStoragePath;

    public FileStorageUtil(FileStorageProperties fileStorageProperties){
        this.archiveStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("archive")).toAbsolutePath().normalize();
        this.reportStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("report")).toAbsolutePath().normalize();
        this.rootStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("root")).toAbsolutePath().normalize();
        try {
            if (!Files.exists(rootStoragePath)){
                Files.createDirectories(rootStoragePath);
            }
            if (!Files.exists(archiveStoragePath)){
                Files.createDirectories(archiveStoragePath);
            }
            if (!Files.exists(reportStoragePath)){
                Files.createDirectories(reportStoragePath);
            }
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the file will be stored", e);
        }
    }

    public String storeArchive(MultipartFile file, String fileName){
        try {
            Path targetPath = this.archiveStoragePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file "+fileName+". Please try again", e);
        }
        return fileName;
    }

    public String storeReport(MultipartFile file, String fileName){
        try {
            Path targetPath = this.reportStoragePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file "+fileName+". Please try again", e);
        }
        return fileName;
    }

    public Resource loadFileAsResource(String pathFromRootDir){
        try {
            Path filePath = this.rootStoragePath.resolve(pathFromRootDir).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            } else {
                throw new StorageFileNotFoundException("File was not found");
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("File was not found", e);
        }
    }

}
