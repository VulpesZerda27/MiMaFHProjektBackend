package com.mima.mimafhprojektbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Data
public class ShoppingBasket {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "shoppingBasket")
    private List<ShoppingBasketItem> shoppingBasketItems;
}
