package com.example.demo.attachment.services;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private static final String BASE_DIRECTORY = "./anexos/";

    public String storeFile(MultipartFile file, String meetingId){
        String path = BASE_DIRECTORY+meetingId;

        File directory = new File(path);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = generateFileName(file.getOriginalFilename());

        File destinationFile = new File(path, fileName);

        try {
            file.transferTo(destinationFile);
            return fileName;
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private String generateFileName(String originalFilename) {
        String name = originalFilename.replaceAll("\\s+", "");
        String extension = "";

        int lastDot = originalFilename.lastIndexOf('.');
        if (lastDot > 0 && lastDot < originalFilename.length() - 1) {
            extension = originalFilename.substring(lastDot);
        }

        return name + "-" + UUID.randomUUID().toString() + extension;
    }
}
