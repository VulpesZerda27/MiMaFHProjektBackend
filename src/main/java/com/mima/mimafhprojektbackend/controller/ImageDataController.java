package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.ImageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageDataController {
    private final ImageDataService imageDataService;

    @GetMapping("/{productId}")
    public ResponseEntity<Resource> downloadProductImage(@PathVariable Long productId) {

        return imageDataService.downloadProductImage(productId);
    }

    @PostMapping(value = "/{productId}", consumes = "multipart/form-data")
    public ResponseEntity<Product> addImageToProduct(@PathVariable Long productId,
                                                     @RequestParam("productImage") MultipartFile imageFile) {
        return imageDataService.addImageToProduct(productId, imageFile);
    }
}
