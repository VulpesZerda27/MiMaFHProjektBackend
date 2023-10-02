package com.mima.mimafhprojektbackend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateAndRetrieveUser() {
        MyUser user = new MyUser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRoles(Arrays.asList("USER"));

        user = entityManager.persist(user);
        entityManager.flush();

        Optional<MyUser> foundUser = userRepository.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getFirstName(), foundUser.get().getFirstName());
        assertEquals(user.getLastName(), foundUser.get().getLastName());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
        assertEquals(user.getRoles(), foundUser.get().getRoles());
    }
}
