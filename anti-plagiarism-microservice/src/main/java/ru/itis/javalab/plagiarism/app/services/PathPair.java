package ru.itis.javalab.plagiarism.app.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class PathPair{
    private String archivePath;
    private String projectPath;
}