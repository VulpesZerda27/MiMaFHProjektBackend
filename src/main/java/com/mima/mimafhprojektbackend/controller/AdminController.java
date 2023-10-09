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
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;

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
    public Author createCategory(@RequestBody @Valid Author author) {
        return adminService.createBookAuthor(author);
    }

    @PutMapping("/bookAuthor/{bookAuthorId}")
    public Author updateBookAuthor(@PathVariable Long bookAuthorId, @RequestBody @Valid Author authorDetails) {
        return adminService.updateBookAuthor(bookAuthorId, authorDetails);
    }

    @DeleteMapping("/bookAuthor/{bookAuthorId}")
    public void deleteBookAuthorById(@PathVariable Long bookAuthorId) {
        adminService.deleteBookAuthorById(bookAuthorId);
    }
    //endregion

    //region Category methods

    @GetMapping("/category")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/category")
    public Category createCategory(@RequestBody @Valid Category category) {
        return adminService.createCategory(category);
    }


    @PutMapping("/category/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody @Valid Category categoryDetails) {
        return adminService.updateCategory(categoryId, categoryDetails);
    }


    @DeleteMapping("/category/{categoryId}")
    public void deleteCategoryById(@PathVariable Long categoryId) {
        adminService.deleteCategoryById(categoryId);
    }
    //endregion

    //region Product methods

    @GetMapping("/product")
    public List<Product> GetAllProducts() {
        return productService.GetAllProducts();
    }

    @PutMapping("/product/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDTO productDetails) {
        return adminService.updateProduct(productId, productDetails);
    }

    @DeleteMapping("/product/{productId}")
    public void deleteProductById(@PathVariable Long productId) {
        adminService.deleteProductById(productId);
    }


    @PostMapping("/product")
    public Product saveProduct(@RequestBody @Valid ProductDTO productDetails) {
        return adminService.saveProduct(productDetails);
    }

    //endregion

    //region User methods

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        adminService.deleteUserById(userId);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long userId, @RequestBody @Valid MyUserDTO userDTO) {
        MyUser myUser = adminService.updateUser(userId, userDTO);
        return new ResponseEntity<>(myUser, HttpStatus.OK);
    }

    @GetMapping("/user")
    public @ResponseBody List<MyUser> GetAllUsers() {
        List<MyUser> toReturn = adminService.GetAllUsers();
        return toReturn;
    }
    //endregion


    }
