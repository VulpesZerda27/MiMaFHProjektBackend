package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.AdminService;
import com.mima.mimafhprojektbackend.service.ImageDataService;
import com.mima.mimafhprojektbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageDataController {
    private final ImageDataService imageDataService;
    private final ProductService productService;
    private final AdminService adminService;

    @GetMapping("/{productId}")
    public ResponseEntity<Resource> downloadProductImage(@PathVariable Long productId) {
        Optional<Product> productOptional = productService.getProductById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productOptional.get();
        String imageName = product.getImageName();

        if (imageName == null || imageName.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Path imagePath = imageDataService.getImagePath(imageName);

        if (!Files.exists(imagePath)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = null;
        try {
            resource = imageDataService.downloadImage(imagePath);
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String mediaType = "image/jpeg";
        if (imageName.toLowerCase().endsWith(".png")) {
            mediaType = "image/png";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{productId}", consumes = "multipart/form-data")
    public ResponseEntity<Product> addImageToProduct(@PathVariable Long productId,
                                                     @RequestParam("productImage") MultipartFile imageFile) {
        String imageUUID;
        if (!imageFile.isEmpty()) {
            try {
                imageUUID = imageDataService.uploadImage(imageFile);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductDTO productDetails = new ProductDTO();
        productDetails.setImageName(imageUUID);

        try {
            Product updatedProduct = adminService.updateProduct(productId, productDetails);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
