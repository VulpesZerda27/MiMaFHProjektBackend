package com.mima.mimafhprojektbackend.controller;


import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.service.ShoppingBasketItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shoppingbasketItems")
public class ShoppingBasketItemController {

    private final ShoppingBasketItemService  shoppingBasketItemService;

    @GetMapping
    public List<ShoppingBasketItem> GetAllShoppingBasketItems() {
        return shoppingBasketItemService.GetAllShoppingBasketItems();
    }
    @GetMapping("/{shoppingBasketItemId}")
    public Optional<ShoppingBasketItem> getShoppingBasketItemById(@PathVariable Long shoppingBasketItemId) {
        return shoppingBasketItemService.getShoppingBasketItemById(shoppingBasketItemId);
    }
    @PostMapping
    public ShoppingBasketItem addShoppingBasketItem(ShoppingBasketItem shoppingBasketItem) {
        return shoppingBasketItemService.addShoppingBasketItem(shoppingBasketItem);
    }

    @PutMapping("/{shoppingBasketItemId}")
    public ShoppingBasketItem updateShoppingBasketItem(@PathVariable Long shoppingBasketItemId, @RequestBody @Valid ShoppingBasketItem shoppingBasketItemDetails) {
        return shoppingBasketItemService.updateShoppingBasketItem(shoppingBasketItemId, shoppingBasketItemDetails);
    }

    @DeleteMapping("/{shoppingBasketItemId}")
    public void deleteShoppingBasketItemById(@PathVariable Long shoppingBasketItemId) {
        shoppingBasketItemService.deleteShoppingBasketItemById(shoppingBasketItemId);
    }


}
