package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.dto.ShoppingBasketItemDTO;
import com.mima.mimafhprojektbackend.model.MyUser;
import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import com.mima.mimafhprojektbackend.repository.ShoppingBasketItemRepository;
import com.mima.mimafhprojektbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingBasketItemService {

    private final ShoppingBasketItemRepository shoppingBasketItemRepository;
    private final UserRepository userRepository;

    public List<ShoppingBasketItem> getShoppingBasketItemsByUserId(Long userId) {
        MyUser user = userRepository.findById(userId).orElseThrow();
        return user.getShoppingBasket().getShoppingBasketItems();
    }

    public ShoppingBasketItem getShoppingBasketItemById(Long basketItemId) {
        return shoppingBasketItemRepository.findById(basketItemId).orElseThrow();
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
