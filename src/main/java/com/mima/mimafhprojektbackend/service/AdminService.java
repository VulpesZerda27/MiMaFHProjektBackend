package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.BookAuthor;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.repository.BookAuthorRepository;
import com.mima.mimafhprojektbackend.repository.CategoryRepository;
import com.mima.mimafhprojektbackend.repository.ProductRepository;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthor createBookAuthor(BookAuthor bookAuthor) {
        return bookAuthorRepository.save(bookAuthor);
    }

    public BookAuthor updateBookAuthor(Long bookAuthorId, BookAuthor bookAuthorDetails) {
        BookAuthor bookAuthor = bookAuthorRepository.findById(bookAuthorId).orElseThrow();
        bookAuthor.setAuthorFirstName(bookAuthorDetails.getAuthorFirstName());
        bookAuthor.setAuthorLastName(bookAuthorDetails.getAuthorLastName());

        return bookAuthorRepository.save(bookAuthor);
    }

    public void deleteBookAuthorById(Long bookAuthorId) {
        bookAuthorRepository.deleteById(bookAuthorId);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        category.setCategoryName(categoryDetails.getCategoryName());
        category.setCategoryId(categoryDetails.getCategoryId());
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public Product saveProduct(Product product, Long bookAuthorId, Long categoryId) {
        if (bookAuthorId != null) {
            BookAuthor bookAuthor = bookAuthorRepository.findById(bookAuthorId).orElseThrow();
            product.setBookAuthor(bookAuthor);
        }
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product productDetails) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setProductDescription(productDetails.getProductDescription());
        product.setProductName(productDetails.getProductName());
        product.setProductPrice(productDetails.getProductPrice());
        product.setProductQuantity(productDetails.getProductQuantity());
        return productRepository.save(product);
    }


    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public MyUser updateUser(Long userId, MyUser userDetails) {
        MyUser user = userRepository.findById(userId).orElseThrow();
        user.setUserFirstName(userDetails.getUserFirstName());
        user.setUserLastName(userDetails.getUserLastName());
        user.setUserEmail(userDetails.getUserEmail());
        user.setUserPassword(userDetails.getUserPassword());
        user.setRoles(userDetails.getRoles());
        return userRepository.save(user);
    }
}
