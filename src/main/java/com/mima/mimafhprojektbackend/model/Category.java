package com.mima.mimafhprojektbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Entity
@Data
public class Category {
    @Id
    @GeneratedValue

    private Long id;
    @NotBlank
    private String name;

}
