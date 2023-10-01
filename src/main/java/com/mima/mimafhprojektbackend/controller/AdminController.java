package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.BookAuthor;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.AdminService;
import com.mima.mimafhprojektbackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public MyUser updateUser(@PathVariable Long userId, @RequestBody @Valid MyUser userDetails) {
        return adminService.updateUser(userId, userDetails);
    }

    @GetMapping
    public @ResponseBody List<MyUser> GetAllUsers() {
        return adminService.GetAllUsers();
    }
    //endregion

    @RestController
    @RequestMapping("/product")
    public class ProductController {

        @Autowired
        ProductService productService;
        private static final String uploadDir = "/path/to/your/upload/directory";

        public ProductController(ProductService productService) {
            this.productService = productService;
        }

        @PostMapping("/add")
        public ResponseEntity<Product> addProduct(@RequestParam("productName") String productName,
                                                  @RequestParam("productPrice") double productPrice,
                                                  @RequestParam("productDescription") String productDescription,
                                                  @RequestParam("productQuantity") String productQuantity,
                                                  @RequestParam("categoryID") Long categoryID,
                                                  @RequestParam("productImage") MultipartFile imageFile,
                                                  @RequestParam("imageName") String imageName) {

            Product product = new Product();
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductDescription(productDescription);

            // Parse productQuantity to int
            int quantity = Integer.parseInt(productQuantity);
            product.setProductQuantity(quantity);

            // Validate categoryID before setting it in the Product object

            // File upload logic
            String imageUUID;
            if (!imageFile.isEmpty()) {
                imageUUID = imageFile.getOriginalFilename();
                Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
                try {
                    Files.write(fileNameAndPath, imageFile.getBytes());
                } catch (Exception e) {
                    // Handle file upload exception
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                imageUUID = imageName;
            }
            product.setImageName(imageUUID);

            // Handle exceptions properly, this is just an example
            try {
                Product newProduct = productService.addProduct(product, categoryID);
                return new ResponseEntity<>(newProduct, HttpStatus.OK);
            } catch (Exception e) {
                // Handle product service exception
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
