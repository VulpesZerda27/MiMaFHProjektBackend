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
        myUser.setUserId(id);
        assertEquals(id, myUser.getUserId());
    }

    @Test
    void testUserFirstName() {
        String firstName = "John";
        myUser.setUserFirstName(firstName);
        assertEquals(firstName, myUser.getUserFirstName());
    }

    @Test
    void testUserLastName() {
        String lastName = "Doe";
        myUser.setUserLastName(lastName);
        assertEquals(lastName, myUser.getUserLastName());
    }

    @Test
    void testUserEmail() {
        String email = "john.doe@example.com";
        myUser.setUserEmail(email);
        assertEquals(email, myUser.getUserEmail());
    }

    @Test
    void testUserPassword() {
        String password = "password123";
        myUser.setUserPassword(password);
        assertEquals(password, myUser.getUserPassword());
    }

    @Test
    void testRoles() {
        myUser.setRoles(Arrays.asList("USER"));
        assertNotNull(myUser.getRoles());
        assertEquals(1, myUser.getRoles().size());
        assertEquals("USER", myUser.getRoles().get(0));
    }

    @Test
    void testShoppingBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        myUser.setShoppingBasket(shoppingBasket);
        assertEquals(shoppingBasket, myUser.getShoppingBasket());
    }
}
