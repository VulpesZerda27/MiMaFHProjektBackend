package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> GetAllProducts() {
        return productService.GetAllProducts();
    }

    @PostMapping
    public Product saveProduct(@RequestBody @Valid Product product) {
        Long bookAuthorId = product.getBookAuthor().getBookAuthorId();
        Long categoryId = product.getCategory().getCategoryId();
        return productService.saveProduct(product, bookAuthorId, categoryId);
    }

    @GetMapping(path="/{productId}")
    public Optional<Product> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }



    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody @Valid Product productDetails) {
        return productService.updateProduct(productId, productDetails);
    }

    @DeleteMapping("/{productId}")
    public void deleteProductById(@PathVariable  Long productId) {
        productService.deleteProductById(productId);
    }
}
