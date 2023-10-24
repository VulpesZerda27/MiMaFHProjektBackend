package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.repository.AuthorRepository;
import com.mima.mimafhprojektbackend.repository.CategoryRepository;
import com.mima.mimafhprojektbackend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void testGetProductById() {
        // Mock data
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        // Mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Action
        Product result = productService.getProductById(productId);

        // Assert
        assertEquals(product, result);
    }

    @Test
    void testSaveProduct() {
        // Mock data
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        Product product = new Product();
        product.setName(productDTO.getName());

        // Mock behavior
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Action
        Product result = productService.saveProduct(productDTO);

        // Assert
        assertEquals(product.getName(), result.getName());
    }

    @Test
    void testUpdateProduct() throws NoSuchElementException {
        // Mock data
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Product");
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Product");

        // Mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Action
        Product result = productService.updateProduct(productId, productDTO);

        // Assert
        assertEquals(productDTO.getName(), result.getName());
    }

    @Test
    void testDeleteProductById() {
        // Mock data
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        // Mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Action
        productService.deleteProductById(productId);

        // Verify delete call
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testDeleteProductById_NotFound() {
        // Mock data
        Long productId = 1L;

        // Mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(NoSuchElementException.class, () -> productService.deleteProductById(productId));
    }
}
