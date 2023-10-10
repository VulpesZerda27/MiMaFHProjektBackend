package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.AdminService;
import com.mima.mimafhprojektbackend.service.BookAuthorService;
import com.mima.mimafhprojektbackend.service.CategoryService;
import com.mima.mimafhprojektbackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BookAuthorService bookAuthorService;

    //region BookAuthor methods
    @GetMapping("/bookAuthor")
    public List<Author> getAllBookAuthors() {
        return bookAuthorService.getAllBookAuthors();
    }

    @PostMapping("/bookAuthor")
    public ResponseEntity<Author> createBookAuthor(@RequestBody @Valid Author author) {
        Author createdAuthor = adminService.createBookAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/bookAuthor/{bookAuthorId}")
    public Author updateBookAuthor(@PathVariable Long bookAuthorId, @RequestBody @Valid Author authorDetails) {
        return adminService.updateBookAuthor(bookAuthorId, authorDetails);
    }

    @DeleteMapping("/bookAuthor/{bookAuthorId}")
    public ResponseEntity<Void> deleteBookAuthorById(@PathVariable Long bookAuthorId) {
        adminService.deleteBookAuthorById(bookAuthorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//endregion

    //region Category methods
    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody @Valid Category category) {
        Category createdCategory = adminService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/category/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody @Valid Category categoryDetails) {
        return adminService.updateCategory(categoryId, categoryDetails);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long categoryId) {
        adminService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//endregion

    //region Product methods
    @GetMapping("/product")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/product/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDTO productDetails) {
        return adminService.updateProduct(productId, productDetails);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        adminService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductDTO productDetails) {
        Product savedProduct = adminService.saveProduct(productDetails);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
//endregion

    //region User methods
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/user/{userId}")
    public MyUser updateUser(@PathVariable Long userId, @RequestBody @Valid MyUserDTO userDTO) {
        return adminService.updateUser(userId, userDTO);
    }

    @GetMapping("/user")
    public List<MyUser> getAllUsers() {
        return adminService.getAllUsers();
    }
//endregion

}
