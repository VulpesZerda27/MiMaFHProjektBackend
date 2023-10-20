package com.mima.mimafhprojektbackend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyUserTest {

    private MyUser myUser;

    @BeforeEach
    void setUp() {
        myUser = new MyUser();
    }

    @Test
    void testUserId() {
        Long id = 1L;
        myUser.setId(id);
        assertEquals(id, myUser.getId());
    }

    @Test
    void testUserFirstName() {
        String firstName = "John";
        myUser.setFirstName(firstName);
        assertEquals(firstName, myUser.getFirstName());
    }

    @Test
    void testUserLastName() {
        String lastName = "Doe";
        myUser.setLastName(lastName);
        assertEquals(lastName, myUser.getLastName());
    }

    @Test
    void testUserEmail() {
        String email = "john.doe@example.com";
        myUser.setEmail(email);
        assertEquals(email, myUser.getEmail());
    }

    @Test
    void testUserPassword() {
        String password = "password123";
        myUser.setPassword(password);
        assertEquals(password, myUser.getPassword());
    }

    @Test
    void testRoles() {

    }

    @Test
    void testShoppingBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        myUser.setShoppingBasket(shoppingBasket);
        assertEquals(shoppingBasket, myUser.getShoppingBasket());
    }
}
