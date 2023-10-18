package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.exceptions.EmailAlreadyRegisteredException;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ShoppingBasketService shoppingBasketService;
    private final ShoppingBasketItemService shoppingBasketItemService;

    public MyUser getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
    public MyUser getUserByEmail(String email) {
        return userRepository.getMyUserByEmail(email).orElseThrow();
    }
    public MyUser createUser(MyUser user) throws EmailAlreadyRegisteredException {
        Optional<MyUser> optionalMyUser = userRepository.getMyUserByEmail(user.getEmail());

        if(optionalMyUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("The email " + user.getEmail() + " is already registered.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String bcryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        user.setRoles(Arrays.asList("USER"));
        user.setEnabled(true);
        user.setShoppingBasket(new ShoppingBasket());
        return userRepository.save(user);
    }

    public void deleteUserById(Long userId) {
        userRepository.findById(userId).orElseThrow();
        userRepository.deleteById(userId);
    }

    public ShoppingBasketItem addItemToUserBasket(Long userId, Long productId) {
        MyUser user = getUserById(userId);
        ShoppingBasket shoppingBasket = user.getShoppingBasket();
        List<ShoppingBasketItem> shoppingBasketItemList = shoppingBasket.getShoppingBasketItems();
        shoppingBasketItemList = shoppingBasketItemList.stream()
                .filter(item -> productId.equals(item.getProduct().getId()))
                .toList();
        if (shoppingBasketItemList.isEmpty()) {
            ShoppingBasketItem shoppingBasketItem = new ShoppingBasketItem();
            shoppingBasketItem.setQuantity(1L);
            Product product = productService.getProductById(productId);
            shoppingBasketItem.setProduct(product);
            shoppingBasketItem.setShoppingBasket(shoppingBasket);
            shoppingBasket.getShoppingBasketItems().add(shoppingBasketItem);
            shoppingBasketService.createShoppingBasket(shoppingBasket);
            return shoppingBasketItem;
        } else {
            ShoppingBasketItem shoppingBasketItem = shoppingBasketItemList.get(0);
            shoppingBasketItem.setQuantity(shoppingBasketItem.getQuantity() + 1);
            return shoppingBasketItemService.addShoppingBasketItem(shoppingBasketItem);
        }
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public MyUser updateUser(Long userId, MyUserDTO userDTO) {
        MyUser user = userRepository.findById(userId).orElseThrow();
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getRoles() != null) {
            user.setRoles(userDTO.getRoles());
        }
        if (userDTO.getEnabled() != null)
            user.setEnabled(userDTO.getEnabled());
        return userRepository.save(user);
    }
}
