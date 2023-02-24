package com.mima.mimafhprojektbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class ShoppingBasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ShoppingBasketItemId;
    @PositiveOrZero
    private int shoppingBasketItemQuantity;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private ShoppingBasket shoppingBasket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
