package com.mima.mimafhprojektbackend.controller;


import com.mima.mimafhprojektbackend.dto.ShoppingBasketItemDTO;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import com.mima.mimafhprojektbackend.service.AuthService;
import com.mima.mimafhprojektbackend.service.ShoppingBasketItemService;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basketItem")
public class ShoppingBasketItemController {

    private final ShoppingBasketItemService  shoppingBasketItemService;
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingBasketItem>> GetAllShoppingBasketItemsOfUser(@PathVariable Long userId) {
        MyUser user = userService.getUserById(userId);
        if (authService.isLoggedInUserOrAdmin(user)) {
            return new ResponseEntity<>(shoppingBasketItemService.getShoppingBasketItemsByUserId(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<ShoppingBasketItem> addShoppingBasketItem(@PathVariable Long userId, @PathVariable Long productId) {
        MyUser user = userService.getUserById(userId);
        if (authService.isLoggedInUser(user)) {
            ShoppingBasketItem shoppingBasketItem = userService.addItemToUserBasket(userId, productId);
            return new ResponseEntity<>(shoppingBasketItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{shoppingBasketItemId}")
    public ResponseEntity<ShoppingBasketItem> updateShoppingBasketItem(@PathVariable Long shoppingBasketItemId, @RequestBody @Valid ShoppingBasketItemDTO shoppingBasketItemDTO) {
        MyUser user = shoppingBasketItemService.getShoppingBasketItemById(shoppingBasketItemId).getShoppingBasket().getUser();
        if (authService.isLoggedInUser(user)) {
            return new ResponseEntity<>(shoppingBasketItemService.updateShoppingBasketItem(shoppingBasketItemId, shoppingBasketItemDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{shoppingBasketItemId}")
    public ResponseEntity<ShoppingBasketItem> deleteShoppingBasketItemById(@PathVariable Long shoppingBasketItemId) {
        MyUser user = shoppingBasketItemService.getShoppingBasketItemById(shoppingBasketItemId).getShoppingBasket().getUser();
        if (authService.isLoggedInUser(user)) {
            shoppingBasketItemService.deleteShoppingBasketItemById(shoppingBasketItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}
