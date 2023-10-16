package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingBasketItemRepository extends JpaRepository <ShoppingBasketItem, Long>{
    List<ShoppingBasketItem> findByShoppingBasketId(Long shoppingBasketId);
    @Query("SELECT sbi FROM ShoppingBasketItem sbi WHERE sbi.product.id = :productId AND sbi.shoppingBasket.id = :shoppingBasketId")
    List<ShoppingBasketItem> findShoppingBasketItemsByProductIdAndBasketId(@Param("productId") Long productId, @Param("shoppingBasketId") Long shoppingBasketId);
}
