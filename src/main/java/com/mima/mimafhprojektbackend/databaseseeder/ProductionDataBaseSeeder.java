package com.mima.mimafhprojektbackend.databaseseeder;

import com.mima.mimafhprojektbackend.model.*;
import com.mima.mimafhprojektbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Profile("production")
@RequiredArgsConstructor
@Component
public class ProductionDataBaseSeeder implements CommandLineRunner {

    private final UserRepository myUserRepository;
    private final RoleRepository roleRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;
    private final List<ShoppingBasket> shoppingBaskets = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionDataBaseSeeder.class);

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting data seeding...");

        MyUser user = new MyUser();
        user.setId(1L);
        user.setFirstName("Addy");
        user.setLastName("Admin");
        user.setEmail("admin@bookshop.com");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String bcryptPassword = passwordEncoder.encode("superSecretAdminPassword");
        user.setPassword(bcryptPassword);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(findOrCreateRoleByName("ADMIN"));
        userRoles.add(findOrCreateRoleByName("USER"));
        user.setRoles(userRoles);

        ShoppingBasket basket = new ShoppingBasket();
        shoppingBaskets.add(basket);
        user.setShoppingBasket(basket);
        user.setEnabled(true);
        List<MyUser> users = new ArrayList<>();
        users.add(user);
        shoppingBasketRepository.saveAll(shoppingBaskets);
        myUserRepository.saveAll(users);

        LOGGER.info("Data seeding complete.");
    }

    private Role findOrCreateRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
    }
}