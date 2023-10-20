package com.mima.mimafhprojektbackend.integration;

//... other imports ...

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.model.Category;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
public class CreateTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{"
                + "\"firstName\": \"John\","
                + "\"lastName\": \"Doe\","
                + "\"email\": \"johndoe1234@example.com\","
                + "\"password\": \"password123!\""
                + "}";

        MvcResult postResult = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = postResult.getResponse().getContentAsString();
        MyUser createdUser = objectMapper.readValue(responseContent, MyUser.class);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("johndoe1234@example.com", createdUser.getEmail());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName("TestProduct");
        productDTO.setPrice(13.99);
        productDTO.setQuantity(5);
        productDTO.setDescription("TestDescription");

        MvcResult postResult = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = postResult.getResponse().getContentAsString();
        Product createdProduct = objectMapper.readValue(responseContent, Product.class);

        assertNotNull(createdProduct);
        assertEquals(productDTO.getName(), createdProduct.getName());
        assertEquals(productDTO.getDescription(), createdProduct.getDescription());
        assertEquals(productDTO.getPrice(), createdProduct.getPrice());
        assertEquals(productDTO.getQuantity(), createdProduct.getQuantity());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("Drama");

        MvcResult postResult = mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = postResult.getResponse().getContentAsString();
        Category createdCategory = objectMapper.readValue(responseContent, Category.class);

        assertNotNull(createdCategory);
        assertEquals(category.getName(), createdCategory.getName());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testCreateAuthor() throws Exception {
        Author author = new Author();
        author.setFirstName("Hans");
        author.setLastName("Wurst");

        MvcResult postResult = mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = postResult.getResponse().getContentAsString();
        Author createdAuthor = objectMapper.readValue(responseContent, Author.class);

        assertNotNull(createdAuthor);
        assertEquals(author.getFirstName(), createdAuthor.getFirstName());
        assertEquals(author.getLastName(), createdAuthor.getLastName());
    }
}