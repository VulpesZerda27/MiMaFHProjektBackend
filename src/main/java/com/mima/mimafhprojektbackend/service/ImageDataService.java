package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private final ProductService productService;
    private final FileService fileService;
    private final String uploadDir = "src/main/java/com/mima/mimafhprojektbackend/images";

    public ResponseEntity<Product> addImageToProduct(Long productId, MultipartFile imageFile){
        String imageUUID;
        if (!imageFile.isEmpty()) {
            try {
                imageUUID = uploadImage(imageFile);
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
            Product updatedProduct = productService.updateProduct(productId, productDetails);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String imageUUID;

        imageUUID = imageFile.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDir, imageUUID);

        fileService.writeToFile(fileNameAndPath, imageFile.getBytes());

        return imageUUID;
    }

    public ResponseEntity<Resource> downloadProductImage(Long productId){
        Product product = productService.getProductById(productId);

        String imageName = product.getImageName();

        if (imageName == null || imageName.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Path imagePath = getImagePath(imageName);

        if (!fileService.fileExists(imagePath)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Resource resource = null;
        try {
            resource = downloadImage(imagePath);
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

    public Resource downloadImage(Path imagePath) throws MalformedURLException {
        Resource resource = getResource(imagePath);
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Failed to read the file!");
        }
        return resource;
    }

    protected Resource getResource(Path imagePath) throws MalformedURLException {
        return new UrlResource(imagePath.toUri());
    }

    public Path getImagePath(String imageName) {
        return Paths.get(uploadDir, imageName);
    }
}
