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
        user.setUserFirstName("John");
        user.setUserLastName("Doe");
        user.setUserEmail("john.doe@example.com");
        user.setUserPassword("password123");
        user.setRoles(Arrays.asList("USER"));

        user = entityManager.persist(user);
        entityManager.flush();

        Optional<MyUser> foundUser = userRepository.findById(user.getUserId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUserFirstName(), foundUser.get().getUserFirstName());
        assertEquals(user.getUserLastName(), foundUser.get().getUserLastName());
        assertEquals(user.getUserEmail(), foundUser.get().getUserEmail());
        assertEquals(user.getUserPassword(), foundUser.get().getUserPassword());
        assertEquals(user.getRoles(), foundUser.get().getRoles());
    }
}
