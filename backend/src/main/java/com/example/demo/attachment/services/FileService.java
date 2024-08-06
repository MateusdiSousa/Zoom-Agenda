package com.example.demo.attachment.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    private static final String BASE_DIRECTORY = "anexos/";

    public String storeFile(MultipartFile file, String meetingId) {
        String path = BASE_DIRECTORY + meetingId;
        File dir = new File(path);

        if (dir.exists()) {
            System.out.println("Diretório existe");
        } 
        else {
            dir.mkdirs();
            System.out.println("Diretório criado: "+ dir.getPath());
        }

        String fileName = generateFileName(file.getOriginalFilename());

        Path destinationPath = Paths.get(path, fileName);

        try {
            Files.copy(file.getInputStream(), destinationPath);
        } catch (IOException e) {
            throw new Error("Erro ao salvar arquivo: "+e.getMessage());
        }

        return fileName;
    }

    private String generateFileName(String originalFilename) {
        int dotIndex = originalFilename.indexOf(".");
        String name = originalFilename.substring(0, dotIndex);
        String extension = originalFilename.substring(dotIndex);
        return name + "-" + UUID.randomUUID().toString() + extension;
    }
}
