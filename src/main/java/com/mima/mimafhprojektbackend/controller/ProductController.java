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
}
