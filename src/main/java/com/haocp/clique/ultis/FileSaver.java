package com.haocp.clique.ultis;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileSaver {

    private static final String UPLOAD_DIR = "uploads";

    public static String save(MultipartFile file, String subFolder) {

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR, subFolder);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String extension = getExtension(originalFileName);
            String newFileName = UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/" + UPLOAD_DIR + "/" + subFolder + "/" + newFileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    private static String getExtension(String fileName) {
        if (fileName == null) return "";
        int index = fileName.lastIndexOf(".");
        return index >= 0 ? fileName.substring(index) : "";
    }
}
