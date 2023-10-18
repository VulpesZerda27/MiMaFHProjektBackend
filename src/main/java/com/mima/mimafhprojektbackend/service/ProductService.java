package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.repository.AuthorRepository;
import com.mima.mimafhprojektbackend.repository.CategoryRepository;
import com.mima.mimafhprojektbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }
    public Product saveProduct(ProductDTO productDetails) {
        Product product = new Product();
        return setProductDetails(productDetails, product);
    }

    public Product updateProduct(Long productId, ProductDTO productDetails) throws NoSuchElementException {
        Product product = productRepository.findById(productId).orElseThrow();
        return setProductDetails(productDetails, product);
    }

    private Product setProductDetails(ProductDTO productDetails, Product product) {
        if(productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if(productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if(productDetails.getPrice() != 0) {
            product.setPrice(productDetails.getPrice());
        }
        if(productDetails.getQuantity() != 0) {
            product.setQuantity(productDetails.getQuantity());
        }
        if(productDetails.getImageName() != null){
            product.setImageName(productDetails.getImageName());
        }
        if(productDetails.getCategory() != null){
            Optional<Category> category = categoryRepository.getCategoryByName(productDetails.getCategory());
            if(category.isPresent()){
                product.setCategory(category.get());
            }
        }
        if(productDetails.getAuthor() != null){
            String[] authorname = productDetails.getAuthor().split(" ");
            Optional<Author> author = authorRepository.getAuthorByFirstNameAndLastName(authorname[0],  authorname[1]);
            if(author.isPresent()){
                product.setAuthor(author.get());
            }
        }

        return productRepository.save(product);
    }


    public void deleteProductById(Long productId) {
        productRepository.findById(productId).orElseThrow();
        productRepository.deleteById(productId);
    }
}
