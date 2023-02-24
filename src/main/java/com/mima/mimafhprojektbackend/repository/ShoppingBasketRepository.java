package com.mima.mimafhprojektbackend.repository;

import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ShoppingBasketRepository extends JpaRepository <ShoppingBasket, Long> {
}
