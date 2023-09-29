package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.model.BookAuthor;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    //region BookAuthor methods
    @PostMapping("/bookAuthor")
    public BookAuthor createCategory(@RequestBody @Valid BookAuthor bookAuthor) {
        return adminService.createBookAuthor(bookAuthor);
    }

    @PutMapping("/bookAuthor/{bookAuthorId}")
    public BookAuthor updateBookAuthor(@PathVariable Long bookAuthorId, @RequestBody @Valid BookAuthor bookAuthorDetails) {
        return adminService.updateBookAuthor(bookAuthorId, bookAuthorDetails);
    }

    @DeleteMapping("/bookAuthor/{bookAuthorId}")
    public void deleteBookAuthorById(@PathVariable Long bookAuthorId) {
        adminService.deleteBookAuthorById(bookAuthorId);
    }
    //endregion

    //region Category methods

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

    @PutMapping("/product/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody @Valid Product productDetails) {
        return adminService.updateProduct(productId, productDetails);
    }


    @DeleteMapping("/product/{productId}")
    public void deleteProductById(@PathVariable Long productId) {
        adminService.deleteProductById(productId);
    }


    @PostMapping("/product")
    public Product saveProduct(@RequestBody @Valid Product product) {
        Long bookAuthorId = product.getBookAuthor().getBookAuthorId();
        Long categoryId = product.getCategory().getCategoryId();
        return adminService.saveProduct(product, bookAuthorId, categoryId);
    }

    //endregion

    //region User methods

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        adminService.deleteUserById(userId);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long userId, @RequestBody @Valid MyUserDTO userDTO) {
        return new ResponseEntity<>(adminService.updateUser(userId, userDTO), HttpStatus.OK);
    }

    @GetMapping
    public @ResponseBody List<MyUser> GetAllUsers() {
        return adminService.GetAllUsers();
    }
    //endregion
}
