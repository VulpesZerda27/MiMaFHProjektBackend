package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.dto.MyUserDTO;
import com.mima.mimafhprojektbackend.exceptions.EmailAlreadyRegisteredException;
import com.mima.mimafhprojektbackend.model.*;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ProductService productService;
    private final ShoppingBasketService shoppingBasketService;
    private final ShoppingBasketItemService shoppingBasketItemService;

    public MyUser getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
    public MyUser getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
    public MyUser createUser(MyUser user) throws EmailAlreadyRegisteredException {
        Optional<MyUser> optionalMyUser = userRepository.findByEmail(user.getEmail());

        if(optionalMyUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("The email " + user.getEmail() + " is already registered.");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String bcryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPassword);
        user.getRoles().add(roleService.findByName("USER"));
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
            Set<Role> userRoles = userDTO.getRoles().stream()
                    .map(roleName -> roleService.findByName(roleName.trim()))
                    .collect(Collectors.toSet());
            user.setRoles(userRoles);
        }

        if (userDTO.getEnabled() != null)
            user.setEnabled(userDTO.getEnabled());
        return userRepository.save(user);
    }
}
