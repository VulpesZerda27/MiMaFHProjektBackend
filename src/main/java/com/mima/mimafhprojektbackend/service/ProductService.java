package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.BookAuthor;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.repository.BookAuthorRepository;
import com.mima.mimafhprojektbackend.repository.CategoryRepository;
import com.mima.mimafhprojektbackend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Product addProduct(Product product, Long categoryId) {
        // Retrieve the category from the database using categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        // Set the category for the product
        product.setCategory(category);

        // Save the product to the database
        return productRepository.save(product);
    }
}
