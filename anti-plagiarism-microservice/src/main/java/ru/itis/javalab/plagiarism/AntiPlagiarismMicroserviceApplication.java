package ru.itis.javalab.plagiarism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.itis.javalab.plagiarism.app.properties.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class AntiPlagiarismMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntiPlagiarismMicroserviceApplication.class, args);
	}

}
