package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.ShoppingBasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingBasketItemRepository extends JpaRepository <ShoppingBasketItem, Long>{
}
