package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
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
