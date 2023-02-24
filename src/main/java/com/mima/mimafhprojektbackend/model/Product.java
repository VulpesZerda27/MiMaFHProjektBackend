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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotBlank
    private String productName;
    @NotBlank
    private String productDescription;
    @Positive
    private double productPrice;
    @PositiveOrZero
    private int productQuantity;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "book_author_id")
    private BookAuthor bookAuthor;


}
