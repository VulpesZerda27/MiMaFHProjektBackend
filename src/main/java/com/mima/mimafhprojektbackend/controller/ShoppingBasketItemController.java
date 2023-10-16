package com.mima.mimafhprojektbackend.controller;


import com.mima.mimafhprojektbackend.dto.ShoppingBasketItemDTO;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.service.ShoppingBasketItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basketItem")
public class ShoppingBasketItemController {

    private final ShoppingBasketItemService  shoppingBasketItemService;

    @GetMapping("/{userId}")
    public List<ShoppingBasketItem> GetAllShoppingBasketItemsOfUser(@PathVariable Long userId) {
        return shoppingBasketItemService.getShoppingBasketItemByUserId(userId);
    }

    @PostMapping
    public ShoppingBasketItem addShoppingBasketItem(ShoppingBasketItem shoppingBasketItem) {
        return shoppingBasketItemService.addShoppingBasketItem(shoppingBasketItem);
    }

    @PutMapping("/{shoppingBasketItemId}")
    public ShoppingBasketItem updateShoppingBasketItem(@PathVariable Long shoppingBasketItemId, @RequestBody @Valid ShoppingBasketItemDTO shoppingBasketItemDTO) {
        return shoppingBasketItemService.updateShoppingBasketItem(shoppingBasketItemId, shoppingBasketItemDTO);
    }

    @DeleteMapping("/{shoppingBasketItemId}")
    public void deleteShoppingBasketItemById(@PathVariable Long shoppingBasketItemId) {
        shoppingBasketItemService.deleteShoppingBasketItemById(shoppingBasketItemId);
    }


}
