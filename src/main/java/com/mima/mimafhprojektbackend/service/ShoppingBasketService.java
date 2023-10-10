package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingBasketService {
    private final ShoppingBasketRepository shoppingBasketRepository;

    public List<ShoppingBasket> GetAllShoppingBaskets() {
        return shoppingBasketRepository.findAll();
    }

    public ShoppingBasket getShoppingBasketById(Long shoppingBasketId) {
        return shoppingBasketRepository.findById(shoppingBasketId).orElseThrow();
    }

    public ShoppingBasket createShoppingBasket(ShoppingBasket shoppingBasket) {
        return shoppingBasketRepository.save(shoppingBasket);
    }

    public void deleteShoppingBasketById(Long shoppingBasketId) {
        shoppingBasketRepository.findById(shoppingBasketId).orElseThrow();
        shoppingBasketRepository.deleteById(shoppingBasketId);
    }
}
