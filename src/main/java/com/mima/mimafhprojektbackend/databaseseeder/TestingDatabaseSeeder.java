package com.mima.mimafhprojektbackend.databaseseeder;

import com.mima.mimafhprojektbackend.model.*;
import com.mima.mimafhprojektbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Profile("testing")
@RequiredArgsConstructor
@Component
public class TestingDatabaseSeeder implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository myUserRepository;
    private final ProductRepository productRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;
    private final ShoppingBasketItemRepository shoppingBasketItemRepository;
    private final RoleRepository roleRepository;
    private final List<ShoppingBasket> shoppingBaskets = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(TestingDatabaseSeeder.class);

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting data seeding...");

        List<Author> authors = loadBookAuthorsFromCSV("/data/book_author.csv");
        authorRepository.saveAll(authors);

        List<Category> categories = loadCategoriesFromCSV("/data/category.csv");
        categoryRepository.saveAll(categories);

        List<MyUser> users = loadMyUsersFromCSV("/data/my_user.csv");
        shoppingBasketRepository.saveAll(shoppingBaskets);
        myUserRepository.saveAll(users);

        List<Product> products = loadProductsFromCSV("/data/product.csv");
        productRepository.saveAll(products);

        List<ShoppingBasketItem> items = loadShoppingBasketItemsFromCSV("/data/shopping_basket_item.csv");
        shoppingBasketItemRepository.saveAll(items);

        LOGGER.info("Data seeding complete.");
    }

    private List<ShoppingBasketItem> loadShoppingBasketItemsFromCSV(String fileName) throws Exception {
        List<ShoppingBasketItem> items = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ShoppingBasketItem item = new ShoppingBasketItem();
                item.setId(Long.parseLong(fields[0].trim()));
                item.setQuantity(Long.parseLong(fields[1].trim()));

                Long basketId = Long.parseLong(fields[2].trim());
                ShoppingBasket basket = shoppingBasketRepository.findById(basketId).orElse(null);
                item.setShoppingBasket(basket);

                Long productId = Long.parseLong(fields[3].trim());
                Product product = productRepository.findById(productId).orElse(null);
                item.setProduct(product);

                items.add(item);
            }
        }
        return items;
    }

    private List<Category> loadCategoriesFromCSV(String fileName) throws Exception {
        List<Category> categories = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Category category = new Category();
                category.setId(Long.parseLong(fields[0].trim()));
                category.setName(fields[1].trim());
                categories.add(category);
            }
        }
        return categories;
    }

    private List<Author> loadBookAuthorsFromCSV(String fileName) throws Exception {
        List<Author> authors = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Author author = new Author();
                author.setId(Long.parseLong(fields[0].trim()));
                author.setFirstName(fields[1].trim());
                author.setLastName(fields[2].trim());
                authors.add(author);
            }
        }
        return authors;
    }


    private List<MyUser> loadMyUsersFromCSV(String fileName) throws Exception {
        List<MyUser> users = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                MyUser user = new MyUser();
                user.setId(Long.parseLong(fields[0].trim()));
                user.setFirstName(fields[1].trim());
                user.setLastName(fields[2].trim());
                user.setEmail(fields[3].trim());

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String bcryptPassword = passwordEncoder.encode(fields[4].trim());
                user.setPassword(bcryptPassword);

                String[] roleFields = fields[5].trim().split(";");
                Set<Role> userRoles = Arrays.stream(roleFields)
                        .map(roleName -> findOrCreateRoleByName(roleName.trim()))
                        .collect(Collectors.toSet());
                user.setRoles(userRoles);

                ShoppingBasket basket = new ShoppingBasket();
                shoppingBaskets.add(basket);
                user.setShoppingBasket(basket);
                user.setEnabled(true);
                users.add(user);
            }
        }
        return users;
    }

    private Role findOrCreateRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
    }



    private List<Product> loadProductsFromCSV(String fileName) throws Exception {
        List<Product> products = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Product product = new Product();
                product.setId(Long.parseLong(fields[0].trim()));
                product.setName(fields[1].trim());
                product.setDescription(fields[2].trim());
                product.setPrice(Double.parseDouble(fields[3].trim()));
                product.setQuantity(Integer.parseInt(fields[4].trim()));

                Long categoryId = Long.parseLong(fields[5].trim());
                Category category = categoryRepository.findById(categoryId).orElse(null);
                product.setCategory(category);

                Long authorId = Long.parseLong(fields[6].trim());
                Author author = authorRepository.findById(authorId).orElse(null);
                product.setAuthor(author);

                product.setImageName(fields[7].trim());

                products.add(product);
            }
        }
        return products;
    }

}
