package com.mima.mimafhprojektbackend;

import com.mima.mimafhprojektbackend.service.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootApplication
@RestController
@RequestMapping("/image")
public class MiMaFhProjektBackendApplication {

    @Autowired
    private ImageDataService service;

    // Define two endpoints: One for Upload image (so it will be stored in the DB)
    //                       One for Download image (so it can be fetched from the DB by filename)

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imagedata=service.downloadImage()
        return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.valueOf("image/png"))
                        .body(imageData);
    }

    public static void main(String[] args) {
        SpringApplication.run(MiMaFhProjektBackendApplication.class, args);
    }

}
