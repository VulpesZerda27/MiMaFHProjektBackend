package com.mima.mimafhprojektbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//test2
@Entity
@Data
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String firstName;
    @NotBlank
    private String lastName;
}
