package com.mima.mimafhprojektbackend.service;

import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ImageDataServiceTest {

    @InjectMocks
    @Spy
    private ImageDataService imageDataService;

    @Mock
    private FileService fileService;

    @Mock
    private ProductService productService;

    @Mock
    private Resource mockResource;

    @Test
    void testDownloadImage() throws Exception {
        Path mockPath = Paths.get("src/main/java/com/mima/mimafhprojektbackend/images/test1.jpg");

        doReturn(mockResource).when(imageDataService).getResource(mockPath);
        when(mockResource.exists()).thenReturn(true);
        when(mockResource.isReadable()).thenReturn(true);
        lenient().when(mockResource.getFilename()).thenReturn("test1.jpg");

        Resource result = imageDataService.downloadImage(mockPath);

        assertEquals(mockResource, result);
    }
    @Test
    void testAddImageToProduct() throws IOException {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        MultipartFile imageFile = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", "some-image-data".getBytes());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setImageName("test.jpg");

        when(productService.updateProduct(eq(productId), any(ProductDTO.class))).thenReturn(product);

        ResponseEntity<Product> result = imageDataService.addImageToProduct(productId, imageFile);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(product, result.getBody());
    }

    @Test
    void testUploadImage() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        String fileName = "test1.jpg";

        when(mockFile.getOriginalFilename()).thenReturn(fileName);
        when(mockFile.getBytes()).thenReturn(new byte[0]);
        doNothing().when(fileService).writeToFile(any(), any());

        String result = imageDataService.uploadImage(mockFile);

        assertEquals(fileName, result);
    }

    @Test
    void testDownloadProductImage() throws Exception {
        for (Long productId = 1L; productId <= 5L; productId++) {
            Path mockPath = Paths.get("src/main/java/com/mima/mimafhprojektbackend/images/test" + productId + ".jpg");
            Product product = new Product();
            product.setId(productId);
            product.setImageName("test" + productId + ".jpg");

            when(productService.getProductById(productId)).thenReturn(product);
            when(fileService.fileExists(any())).thenReturn(true);
            doReturn(mockResource).when(imageDataService).getResource(mockPath);
            when(mockResource.exists()).thenReturn(true);
            when(mockResource.isReadable()).thenReturn(true);
            lenient().when(mockResource.getFilename()).thenReturn("test" + productId + ".jpg");

            ResponseEntity<Resource> result = imageDataService.downloadProductImage(productId);

            assertEquals(HttpStatus.OK, result.getStatusCode());
        }
    }

    @Test
    void testDownloadProductImage_NotFound() {
        Long productId = 6L;
        Product product = new Product();
        product.setId(productId);

        when(productService.getProductById(productId)).thenReturn(product);

        ResponseEntity<Resource> result = imageDataService.downloadProductImage(productId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
