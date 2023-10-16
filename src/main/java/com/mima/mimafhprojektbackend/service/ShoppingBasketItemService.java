package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.dto.ShoppingBasketItemDTO;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketItemRepository;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingBasketItemService {

    private final ShoppingBasketItemRepository shoppingBasketItemRepository;
    private final UserRepository userRepository;

    public List<ShoppingBasketItem> GetAllShoppingBasketItems() {
        return shoppingBasketItemRepository.findAll();
    }

    public List<ShoppingBasketItem> getShoppingBasketItemByUserId(Long userId) {
        Long shoppingBasketId = userRepository.findShoppingBasketIdByUserId(userId).orElseThrow();
        return shoppingBasketItemRepository.findByShoppingBasketId(shoppingBasketId);
    }

    public List<ShoppingBasketItem> getShoppingBasketItemByUserIdAndBasketId(Long productId, Long basketId){
        return shoppingBasketItemRepository.findShoppingBasketItemsByProductIdAndBasketId(productId, basketId);
    }

    public ShoppingBasketItem addShoppingBasketItem(ShoppingBasketItem shoppingBasketItem) {
        return shoppingBasketItemRepository.save(shoppingBasketItem);
    }

    public ShoppingBasketItem updateShoppingBasketItem(Long shoppingBasketItemId, ShoppingBasketItemDTO shoppingBasketItemDTO) {
        ShoppingBasketItem shoppingBasketItem = shoppingBasketItemRepository.findById(shoppingBasketItemId).orElseThrow();
        shoppingBasketItem.setQuantity(shoppingBasketItemDTO.getQuantity());
        return shoppingBasketItemRepository.save(shoppingBasketItem);
    }

    public void deleteShoppingBasketItemById(Long shoppingBasketItemId) {
        shoppingBasketItemRepository.findById(shoppingBasketItemId).orElseThrow();
        shoppingBasketItemRepository.deleteById(shoppingBasketItemId);
    }
}
