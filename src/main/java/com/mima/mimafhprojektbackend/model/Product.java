package com.mima.mimafhprojektbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;
    @Positive
    private double price;
    @PositiveOrZero
    private int quantity;

    private String imageName;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ShoppingBasketItem> shoppingBasketItems = new ArrayList<>();

}
