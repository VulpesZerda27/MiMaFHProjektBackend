package com.mima.mimafhprojektbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
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


}
