package com.mima.mimafhprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingBasketItem {
    @Id
    @GeneratedValue
    private Long ShoppingBasketItemId;
    @PositiveOrZero
    private int shoppingBasketItemQuantity;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    @JsonIgnore
    private ShoppingBasket shoppingBasket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
