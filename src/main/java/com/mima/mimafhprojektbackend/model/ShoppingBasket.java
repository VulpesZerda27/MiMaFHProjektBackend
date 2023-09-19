package com.mima.mimafhprojektbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Data
public class ShoppingBasket {
    @Id
    @GeneratedValue
    private Long shoppingBasketId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private MyUser user;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "shoppingBasket")
    private List<ShoppingBasketItem> shoppingBasketItems;


}
