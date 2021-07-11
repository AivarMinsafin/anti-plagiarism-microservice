package ru.itis.javalab.plagiarism.app.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.javalab.plagiarism.app.properties.FileStorageProperties;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Component
public class FileStorageUtilImpl implements FileStorageUtil {

    private final Path archiveStoragePath;
    private final Path reportStoragePath;
    private final Path projectStoragePath;

    @Autowired
    public FileStorageUtilImpl(FileStorageProperties fileStorageProperties){
        this.archiveStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("archive")).toAbsolutePath().normalize();
        this.reportStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("report")).toAbsolutePath().normalize();
        this.projectStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("project")).toAbsolutePath().normalize();
        try {
            if (!Files.exists(projectStoragePath)){
                Files.createDirectories(projectStoragePath);
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

    @Override
    public String storeArchive(MultipartFile file, String path, String fileName, String fileExt) {
        try {
            Path targetPath = this.archiveStoragePath.resolve(path).resolve(fileName+"."+fileExt);
            if (!Files.exists(targetPath.getParent())){
                Files.createDirectories(targetPath.getParent());
            }
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            String projectPath = this.projectStoragePath.resolve(path).resolve(fileName).toAbsolutePath().toString();
            extractArchive(targetPath.toString(), projectPath);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file "+path+". Please try again", e);
        }
        return path;
    }

    @Override
    public String storeReport(MultipartFile file, String fileName) {
        try {
            Path targetPath = this.reportStoragePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file "+fileName+". Please try again", e);
        }
        return fileName;
    }

    @Override
    public Resource loadFileAsResource(String path, String type) {
        Path storagePath;
        switch (type){
            case "archive":
                storagePath = archiveStoragePath;
                break;
            case "report":
                storagePath = reportStoragePath;
                break;
            default:
                throw new FileStorageException("Wrong file type");
        }
        try {
            Path filePath = storagePath.resolve(path).normalize();
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

    @Override
    public void extractArchive(String fileZip, String destDir) {
        try {
            ZipFile zipFile = new ZipFile(this.archiveStoragePath.resolve(fileZip).toAbsolutePath().normalize().toString());
            if (zipFile.isEncrypted()){
                throw new FileStorageException("File should not be encrypted");
            }
            zipFile.extractAll(destDir);
        } catch (ZipException e) {
            throw new FileStorageException("Extraction problems...", e);
        }
    }

}
