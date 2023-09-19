package com.mima.mimafhprojektbackend.controller;


import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.service.ShoppingBasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shoppingbasket")
public class ShoppingBasketController
{
    private final ShoppingBasketService  shoppingBasketService;

    @GetMapping
    public List<ShoppingBasket> GetAllShoppingBaskets() {
        return shoppingBasketService.GetAllShoppingBaskets();
    }
    @GetMapping("/{shoppingBasketId}")
    public Optional<ShoppingBasket> getShoppingBasketById(@PathVariable Long shoppingBasketId) {
        return shoppingBasketService.getShoppingBasketById(shoppingBasketId);
    }

    @PostMapping
    public ShoppingBasket createShoppingBasket(@RequestBody @Valid ShoppingBasket shoppingBasket) {
        return shoppingBasketService.createShoppingBasket(shoppingBasket);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{shoppingBasketId}")
    public void deleteShoppingBasketById(@PathVariable Long shoppingBasketId) {
        shoppingBasketService.deleteShoppingBasketById(shoppingBasketId);
    }
}
