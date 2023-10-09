package com.mima.mimafhprojektbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageDataService {
    private final String uploadDir = "src/main/java/com/mima/mimafhprojektbackend/images";
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String imageUUID;

        imageUUID = imageFile.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDir, imageUUID);

        Files.write(fileNameAndPath, imageFile.getBytes());

        return imageUUID;
    }

    public Resource downloadImage(Path imagePath) throws MalformedURLException {
        Resource resource = new UrlResource(imagePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Failed to read the file!");
        }
        return resource;
    }

    public Path getImagePath(String imageName) {
        return Paths.get(uploadDir, imageName);
    }
}
