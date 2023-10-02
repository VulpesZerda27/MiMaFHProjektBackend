package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.dto.MyUserDTO;
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
    public Product updateProduct(@PathVariable Long productId, @RequestBody @Valid Product productDetails) {
        return adminService.updateProduct(productId, productDetails);
    }


    @DeleteMapping("/product/{productId}")
    public void deleteProductById(@PathVariable Long productId) {
        adminService.deleteProductById(productId);
    }


    @PostMapping("/product")
    public Product saveProduct(@RequestBody @Valid Product product) {
        Long bookAuthorId = product.getAuthor().getId();
        Long categoryId = product.getCategory().getId();
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

    @GetMapping("/user")
    public @ResponseBody List<MyUser> GetAllUsers() {
        List<MyUser> toReturn = adminService.GetAllUsers();
        return toReturn;
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
