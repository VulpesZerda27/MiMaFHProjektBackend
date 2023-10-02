package com.mima.mimafhprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingBasketItem {
    @Id
    @GeneratedValue
    private Long id;
    @PositiveOrZero
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "shopping_basket_id")
    @JsonIgnore
    private ShoppingBasket shoppingBasket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
