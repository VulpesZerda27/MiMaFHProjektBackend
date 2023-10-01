package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.repository.BookAuthorRepository;
import com.mima.mimafhprojektbackend.repository.CategoryRepository;
import com.mima.mimafhprojektbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, BookAuthorRepository bookAuthorRepository, CategoryRepository categoryRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> GetAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

}
