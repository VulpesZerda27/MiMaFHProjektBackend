package com.mima.mimafhprojektbackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Data
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookAuthorId;
    @NotNull
    private String authorFirstName;
    @NotBlank
    private String authorLastName;
}
