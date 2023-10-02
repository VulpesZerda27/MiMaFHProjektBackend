package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.model.Author;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookAuthorRepository bookAuthorRepository;

    public Author createBookAuthor(Author author) {
        return bookAuthorRepository.save(author);
    }

    public Author updateBookAuthor(Long bookAuthorId, Author authorDetails) {
        Author author = bookAuthorRepository.findById(bookAuthorId).orElseThrow();
        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());

        return bookAuthorRepository.save(author);
    }

    public void deleteBookAuthorById(Long bookAuthorId) {
        bookAuthorRepository.deleteById(bookAuthorId);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category categoryDetails) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        category.setName(categoryDetails.getName());
        category.setId(categoryDetails.getId());
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public Product saveProduct(Product product, Long bookAuthorId, Long categoryId) {
        if (bookAuthorId != null) {
            Author author = bookAuthorRepository.findById(bookAuthorId).orElseThrow();
            product.setAuthor(author);
        }
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product productDetails) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setDescription(productDetails.getDescription());
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        return productRepository.save(product);
    }


    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public void deleteUserById(Long userId) { userRepository.deleteById(userId); }
    public List<MyUser> GetAllUsers() {
        return userRepository.findAll();
    }

    public MyUser updateUser(Long userId, MyUserDTO userDTO) {
        MyUser user = userRepository.findById(userId).orElseThrow();
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getRoles() != null) {
            user.setRoles(userDTO.getRoles());
        }
        if (userDTO.getEnabled() != null)
            user.setEnabled(userDTO.getEnabled());
        return userRepository.save(user);
    }
}
