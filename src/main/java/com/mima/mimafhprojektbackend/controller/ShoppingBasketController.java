package com.mima.mimafhprojektbackend.controller;


import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.service.ShoppingBasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ShoppingBasket getShoppingBasketById(@PathVariable Long shoppingBasketId) {
        return shoppingBasketService.getShoppingBasketById(shoppingBasketId);
    }
}
