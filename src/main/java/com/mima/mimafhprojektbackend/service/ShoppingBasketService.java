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

    public ShoppingBasket createShoppingBasket(ShoppingBasket shoppingBasket) {
        return shoppingBasketRepository.save(shoppingBasket);
    }
}
