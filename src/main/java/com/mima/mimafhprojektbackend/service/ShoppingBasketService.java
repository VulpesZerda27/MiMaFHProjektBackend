package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingBasketService {
    private final ShoppingBasketRepository shoppingBasketRepository;

    public ShoppingBasketService(ShoppingBasketRepository shoppingBasketRepository) {
        this.shoppingBasketRepository = shoppingBasketRepository;
    }

    public List<ShoppingBasket> GetAllShoppingBaskets() {
        return shoppingBasketRepository.findAll();
    }

    public Optional<ShoppingBasket> getShoppingBasketById(Long shoppingBasketId) {
        return shoppingBasketRepository.findById(shoppingBasketId);
    }

    public ShoppingBasket createShoppingBasket(ShoppingBasket shoppingBasket) {
        return shoppingBasketRepository.save(shoppingBasket);
    }

    public void deleteShoppingBasketById(Long shoppingBasketId) {
        shoppingBasketRepository.deleteById(shoppingBasketId);
    }
}
