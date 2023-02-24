package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingBasketItemService {

    private final ShoppingBasketItemRepository shoppingBasketItemRepository;

    public ShoppingBasketItemService(ShoppingBasketItemRepository shoppingBasketItemRepository){
        this.shoppingBasketItemRepository = shoppingBasketItemRepository;
    }


    public List<ShoppingBasketItem> GetAllShoppingBasketItems() {
        return shoppingBasketItemRepository.findAll();
    }

    public Optional<ShoppingBasketItem> getShoppingBasketItemById(Long shoppingBasketItemId) {
        return shoppingBasketItemRepository.findById(shoppingBasketItemId);
    }

    public ShoppingBasketItem addShoppingBasketItem(ShoppingBasketItem shoppingBasketItem) {
        return shoppingBasketItemRepository.save(shoppingBasketItem);
    }

    public ShoppingBasketItem updateShoppingBasketItem(Long shoppingBasketItemId, ShoppingBasketItem shoppingBasketItemDetails) {
        ShoppingBasketItem shoppingBasketItem = shoppingBasketItemRepository.findById(shoppingBasketItemId).orElseThrow();
        shoppingBasketItem.setShoppingBasketItemQuantity(shoppingBasketItemDetails.getShoppingBasketItemQuantity());
        return shoppingBasketItemRepository.save(shoppingBasketItem);
    }

    public void deleteShoppingBasketItemById(Long shoppingBasketItemId) {
        shoppingBasketItemRepository.deleteById(shoppingBasketItemId);
    }
}
