package com.mima.mimafhprojektbackend.controller;


import com.mima.mimafhprojektbackend.dto.ShoppingBasketItemDTO;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import com.mima.mimafhprojektbackend.security.UserPrincipal;
import com.mima.mimafhprojektbackend.service.ShoppingBasketItemService;
import com.mima.mimafhprojektbackend.service.ShoppingBasketService;
import com.mima.mimafhprojektbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basketItem")
public class ShoppingBasketItemController {

    private final ShoppingBasketItemService  shoppingBasketItemService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public List<ShoppingBasketItem> GetAllShoppingBasketItemsOfUser(@PathVariable Long userId) {
        return shoppingBasketItemService.getShoppingBasketItemByUserId(userId);
    }

    @PostMapping
    public ShoppingBasketItem addShoppingBasketItem(ShoppingBasketItem shoppingBasketItem) {
        return shoppingBasketItemService.addShoppingBasketItem(shoppingBasketItem);
    }

    @PutMapping("/{shoppingBasketItemId}")
    public ResponseEntity<ShoppingBasketItem> updateShoppingBasketItem(@PathVariable Long shoppingBasketItemId, @RequestBody @Valid ShoppingBasketItemDTO shoppingBasketItemDTO) {
        ShoppingBasketItem shoppingBasketItem = shoppingBasketItemService.getShoppingBasketItemById(shoppingBasketItemId);
        ShoppingBasket shoppingBasket = shoppingBasketItem.getShoppingBasket();
        MyUser user = userService.getUserByShoppingBasketId(shoppingBasket.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(shoppingBasketItemService.updateShoppingBasketItem(shoppingBasketItemId, shoppingBasketItemDTO), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{shoppingBasketItemId}")
    public void deleteShoppingBasketItemById(@PathVariable Long shoppingBasketItemId) {
        shoppingBasketItemService.deleteShoppingBasketItemById(shoppingBasketItemId);
    }


}
