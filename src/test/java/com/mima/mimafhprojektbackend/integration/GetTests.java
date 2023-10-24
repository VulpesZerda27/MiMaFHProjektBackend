package com.mima.mimafhprojektbackend.integration;

//... other imports ...

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mima.mimafhprojektbackend.dto.ProductDTO;
import com.mima.mimafhprojektbackend.model.*;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
public class GetTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    public void testGetUser() throws Exception {
        when(authService.isLoggedInUserOrAdmin(any())).thenReturn(true);

        Long userId = 1L;

        MvcResult getResult = mockMvc.perform(get("/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String getResponseContent = getResult.getResponse().getContentAsString();
        MyUser foundUser = objectMapper.readValue(getResponseContent, MyUser.class);

        assertNotNull(foundUser);
        assertEquals("Alice", foundUser.getFirstName());
        assertEquals("Wonderland", foundUser.getLastName());
        assertEquals("alice@bookshop.com", foundUser.getEmail());
        assertNull(foundUser.getPassword());
    }

    @Test
    public void testGetProduct() throws Exception {
        Long productId = 1L;

        MvcResult getResult = mockMvc.perform(get("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String getResponseContent = getResult.getResponse().getContentAsString();
        Product foundProduct = objectMapper.readValue(getResponseContent, Product.class);

        assertNotNull(foundProduct);
        assertEquals("Achtsam Morden", foundProduct.getName());
        assertEquals(14.99, foundProduct.getPrice());
        assertEquals(100, foundProduct.getQuantity());
    }

    @Test
    public void testGetCategory() throws Exception {
        Long categoryId = 1L;

        MvcResult getResult = mockMvc.perform(get("/category/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String getResponseContent = getResult.getResponse().getContentAsString();
        Category foundCategory = objectMapper.readValue(getResponseContent, Category.class);


        assertNotNull(foundCategory);
        assertEquals("Krimis", foundCategory.getName());
    }

    @Test
    public void testGetAuthor() throws Exception {
        Long authorId = 1L;

        MvcResult getResult = mockMvc.perform(get("/author/" + authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String getResponseContent = getResult.getResponse().getContentAsString();
        Author foundAuthor = objectMapper.readValue(getResponseContent, Author.class);

        assertNotNull(foundAuthor);
        assertEquals("Karsten", foundAuthor.getFirstName());
        assertEquals("Dusse", foundAuthor.getLastName());
    }

    @Test
    public void testGetBasketItems() throws Exception {
        when(authService.isLoggedInUserOrAdmin(any())).thenReturn(true);
        Long userId = 1L;

        MvcResult getResult = mockMvc.perform(get("/basketItem/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String getResponseContent = getResult.getResponse().getContentAsString();
        List<ShoppingBasketItem> shoppingBasketItemList = objectMapper.readValue(getResponseContent, new TypeReference<List<ShoppingBasketItem>>(){});

        assertNotNull(shoppingBasketItemList);
        assertEquals(1L, shoppingBasketItemList.get(0).getProduct().getId());
        assertEquals(2L, shoppingBasketItemList.get(1).getProduct().getId());
    }
}