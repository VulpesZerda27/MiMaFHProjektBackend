package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> GetAllProducts() {
        return productService.GetAllProducts();
    }

    @GetMapping(path="/{productId}")
    public Optional<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/image/{productId}")
    public ResponseEntity<Resource> downloadProductImage(@PathVariable Long productId) {
        String uploadDir = "src/main/java/com/mima/mimafhprojektbackend/images";
        // Fetch the product
        Optional<Product> productOptional = productService.getProductById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = productOptional.get();
        String imageName = product.getImageName();

        // If the image name is null or empty, return not found
        if (imageName == null || imageName.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Construct the path to the image
        Path imagePath = Paths.get(uploadDir, imageName);

        // Check if file exists
        if (!Files.exists(imagePath)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Serve the image content
        Resource resource = null;
        try {
            resource = new UrlResource(imagePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Failed to read the file!");
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String mediaType = "image/jpeg";  // Default to jpeg, adjust based on your need
        if (imageName.toLowerCase().endsWith(".png")) {
            mediaType = "image/png";
        } // You can add more types like GIF, BMP etc.

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
