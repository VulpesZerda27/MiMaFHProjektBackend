package com.mima.mimafhprojektbackend.databaseseeder;

import com.mima.mimafhprojektbackend.model.*;
import com.mima.mimafhprojektbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("seed")
@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository myUserRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingBasketRepository shoppingBasketRepository;
    @Autowired
    private ShoppingBasketItemRepository shoppingBasketItemRepository;

    @Override
    public void run(String... args) throws Exception {
        // Populate BookAuthors
        List<BookAuthor> bookAuthors = loadBookAuthorsFromCSV("/data/book_author.csv");
        bookAuthorRepository.saveAll(bookAuthors);

        // Populate Categories
        List<Category> categories = loadCategoriesFromCSV("/data/category.csv");
        categoryRepository.saveAll(categories);


        // Populate MyUsers
        List<MyUser> users = loadMyUsersFromCSV("/data/my_user.csv");
        myUserRepository.saveAll(users);

        // Populate Products
        List<Product> products = loadProductsFromCSV("/data/product.csv");
        productRepository.saveAll(products);

        // Populate ShoppingBaskets
        /*List<ShoppingBasket> baskets = loadShoppingBasketsFromCSV("/data/shopping_basket.csv");
        shoppingBasketRepository.saveAll(baskets);
        */
        // Populate ShoppingBasketItems
        List<ShoppingBasketItem> items = loadShoppingBasketItemsFromCSV("/data/shopping_basket_item.csv");
        shoppingBasketItemRepository.saveAll(items);
    }

    private List<ShoppingBasket> loadShoppingBasketsFromCSV(String fileName) throws Exception {
        List<ShoppingBasket> baskets = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ShoppingBasket basket = new ShoppingBasket();
                basket.setShoppingBasketId(Long.parseLong(fields[0].trim()));
                // Logic to link to MyUser can be added here.
                baskets.add(basket);
            }
        }
        return baskets;
    }

    private List<ShoppingBasketItem> loadShoppingBasketItemsFromCSV(String fileName) throws Exception {
        List<ShoppingBasketItem> items = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ShoppingBasketItem item = new ShoppingBasketItem();
                item.setShoppingBasketItemId(Long.parseLong(fields[0].trim()));
                item.setShoppingBasketItemQuantity(Integer.parseInt(fields[1].trim()));

                // Linking to ShoppingBasket
                Long basketId = Long.parseLong(fields[2].trim());
                ShoppingBasket basket = shoppingBasketRepository.findById(basketId).orElse(null);
                item.setShoppingBasket(basket);

                // Linking to Product
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
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Category category = new Category();
                category.setCategoryId(Long.parseLong(fields[0].trim()));
                category.setCategoryName(fields[1].trim());
                categories.add(category);
            }
        }
        return categories;
    }

    private List<BookAuthor> loadBookAuthorsFromCSV(String fileName) throws Exception {
        List<BookAuthor> authors = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                BookAuthor author = new BookAuthor();
                author.setBookAuthorId(Long.parseLong(fields[0].trim()));
                author.setAuthorFirstName(fields[1].trim());
                author.setAuthorLastName(fields[2].trim());
                authors.add(author);
            }
        }
        return authors;
    }


    private List<MyUser> loadMyUsersFromCSV(String fileName) throws Exception {
        List<MyUser> users = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                MyUser user = new MyUser();
                user.setUserId(Long.parseLong(fields[0].trim()));
                user.setUserFirstName(fields[1].trim());
                user.setUserLastName(fields[2].trim());
                user.setUserEmail(fields[3].trim());

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String bcryptPassword = passwordEncoder.encode(fields[4].trim());
                user.setUserPassword(bcryptPassword);

                String[] roleFields = fields[5].trim().split(";");
                user.setRoles(Arrays.asList(roleFields));

                // Create a new ShoppingBasket for the user and set it
                ShoppingBasket basket = new ShoppingBasket();
                user.setShoppingBasket(basket);   // this will also set the user for the basket because of the bidirectional association

                users.add(user);
            }
        }
        return users;
    }


    private List<Product> loadProductsFromCSV(String fileName) throws Exception {
        List<Product> products = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);  // Use ClassPathResource to load the CSV
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Product product = new Product();
                product.setProductId(Long.parseLong(fields[0].trim()));
                product.setProductName(fields[1].trim());
                product.setProductDescription(fields[2].trim());
                product.setProductPrice(Double.parseDouble(fields[3].trim()));
                product.setProductQuantity(Integer.parseInt(fields[4].trim()));

                // Linking to Category
                Long categoryId = Long.parseLong(fields[5].trim());
                Category category = categoryRepository.findById(categoryId).orElse(null);
                product.setCategory(category);

                // Linking to BookAuthor
                Long authorId = Long.parseLong(fields[6].trim());
                BookAuthor author = bookAuthorRepository.findById(authorId).orElse(null);
                product.setBookAuthor(author);

                products.add(product);
            }
        }
        return products;
    }

}
