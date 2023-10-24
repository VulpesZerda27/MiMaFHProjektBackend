package com.mima.mimafhprojektbackend.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {
    public boolean fileExists(Path path) {
        return Files.exists(path);
    }

    public byte[] readFile(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    public void writeToFile(Path path, byte[] data) throws IOException {
        Files.write(path, data);
    }
}
