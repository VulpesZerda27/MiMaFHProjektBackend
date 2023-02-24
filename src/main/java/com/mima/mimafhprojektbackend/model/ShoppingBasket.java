package com.mima.mimafhprojektbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class ShoppingBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingBasketId;

    @OneToOne
    @JoinColumn(name = "shoppingBasket")
    private MyUser user;

    @OneToMany(mappedBy = "shoppingBasket")
    private List<ShoppingBasketItem> shoppingBasketItems;


}
