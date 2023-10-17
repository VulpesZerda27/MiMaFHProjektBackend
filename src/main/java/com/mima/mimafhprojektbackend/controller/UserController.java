package com.mima.mimafhprojektbackend.controller;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.Product;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import com.mima.mimafhprojektbackend.security.UserPrincipalAuthenticationToken;
import com.mima.mimafhprojektbackend.service.ProductService;
import com.mima.mimafhprojektbackend.service.ShoppingBasketItemService;
import com.mima.mimafhprojektbackend.service.ShoppingBasketService;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final ShoppingBasketService shoppingBasketService;
    private final ShoppingBasketItemService shoppingBasketItemService;

    @GetMapping("/{userid}")
    public MyUser getUserById(@PathVariable Long userid) {
        return userService.getUserById(userid);
    }

    @PostMapping
    public ResponseEntity<MyUser> createUser(@Valid @RequestBody MyUser myUser) {
        MyUser createdUser = userService.createUser(myUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/basket/{userId}/{productId}")
    public ResponseEntity<ShoppingBasket> addItemToUserBasket(@PathVariable Long userId, @PathVariable Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUserId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            MyUser user = userService.getUserById(userId);
            ShoppingBasket shoppingBasket = user.getShoppingBasket();
            List<ShoppingBasketItem> shoppingBasketItemList = shoppingBasketItemService.getShoppingBasketItemByUserIdAndBasketId(productId, shoppingBasket.getId());
            if (shoppingBasketItemList.isEmpty()) {
                ShoppingBasketItem shoppingBasketItem = new ShoppingBasketItem();
                shoppingBasketItem.setQuantity(1L);
                Product product = productService.getProductById(productId);
                shoppingBasketItem.setProduct(product);
                shoppingBasketItem.setShoppingBasket(shoppingBasket);
                List<ShoppingBasketItem> shoppingBasketItems = shoppingBasket.getShoppingBasketItems();
                shoppingBasketItems.add(shoppingBasketItem);
                shoppingBasket.setShoppingBasketItems(shoppingBasketItems);
                shoppingBasketService.createShoppingBasket(shoppingBasket);
            } else {
                ShoppingBasketItem shoppingBasketItem = shoppingBasketItemList.get(0);
                shoppingBasketItem.setQuantity(shoppingBasketItem.getQuantity() + 1);
                shoppingBasketItemService.addShoppingBasketItem(shoppingBasketItem);
            }
            return new ResponseEntity<>(shoppingBasket, HttpStatus.OK);
        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUserId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    }
