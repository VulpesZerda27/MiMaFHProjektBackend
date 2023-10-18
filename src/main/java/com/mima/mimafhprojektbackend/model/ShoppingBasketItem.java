package com.mima.mimafhprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class ShoppingBasketItem {
    @Id
    @GeneratedValue
    private Long id;

    @PositiveOrZero
    private Long quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shopping_basket_id")
    private ShoppingBasket shoppingBasket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
