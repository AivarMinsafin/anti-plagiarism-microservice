package ru.itis.javalab.plagiarism.app.utils.plagiarism.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalab.plagiarism.app.properties.FileStorageProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReportPlagiarismCheckUtilImpl implements ReportPlagiarismCheckUtil {

    private final Path reportsStoragePath;

    @Autowired
    public ReportPlagiarismCheckUtilImpl(FileStorageProperties fileStorageProperties) {
        this.reportsStoragePath = Paths.get(fileStorageProperties.getUploadDir().get("report"));
    }

    PDDocument deleteExcessContent(PDDocument document1) {
        document1.removePage(0);
        document1.removePage(1);
        document1.removePage(2);
        document1.removePage(document1.getPages().getCount() - 1);
        document1.removePage(document1.getPages().getCount() - 2);
        return document1;
    }

    String getTextOfDoc(PDDocument doc) {
        String resultText = "";
        try {
            if (!doc.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                resultText = stripper.getText(doc);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return resultText;
    }

    DocumentVector prepareDocumentVector(File file){
        PDDocument document;
        DocumentVector resultVector = new DocumentVector();
        try {
            document = PDDocument.load(file);
            document = deleteExcessContent(document);
            String text = getTextOfDoc(document);
            for (String w: text.split("[,. \\r\\n]")) {
                if (w.equals("")) {
                    continue;
                }
                if (w.matches("_+")) {
                    continue;
                }
                resultVector.incCount(w);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return resultVector;
    }

    double getSimilarityOfTwoDocuments(DocumentVector mainDocVector, File file) {
        DocumentVector secondDocVector = prepareDocumentVector(file);
        return mainDocVector.getCosineSimilarityWith(secondDocVector);
    }

    DocumentVector getMainDocumentVector(List<File> files, String mainReportName) {
        DocumentVector result = new DocumentVector();
        for (File e : files) {
            if (e.getName().contains(mainReportName)) {
                result = prepareDocumentVector(e);
                break;
            }
        }
        return result;
    }

    @Override
    public Map<String, String> findSimilarityMap(Long courseId, String studentFirstName, String studentLastName, Long studentId) {
        Path rootDir = this.reportsStoragePath.resolve(courseId.toString());
        String mainReportName = studentFirstName.concat("_").concat(studentLastName).concat("_").concat(studentId.toString())
                .concat("_").concat(courseId.toString());
        List<File> filesInFolder;
        Map<String, String> resultMap = new HashMap<>();
        try {
            filesInFolder = Files.walk(rootDir)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            if (filesInFolder.size() < 2){
                return new HashMap<>();
            }
            DocumentVector mainDocVector = getMainDocumentVector(filesInFolder, mainReportName);
            for (File e : filesInFolder) {
                if (e.getName().contains(mainReportName)) {
                    continue;
                }
                double percent = getSimilarityOfTwoDocuments(mainDocVector, e)*100;
                resultMap.put(e.getName(), String.format("%.2f", percent));
            }
        } catch (IOException e) {
            throw new ReportPlagiarismCheckUtilException(e);
        }
        return resultMap;
    }
}
