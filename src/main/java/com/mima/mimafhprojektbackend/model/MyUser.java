package com.mima.mimafhprojektbackend.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class MyUser {
    @Id
    @GeneratedValue
    private Long userId;
    @NotBlank
    @Size(min = 4, max = 20)
    private String userName;
    @NotBlank
    private String userFirstName;
    @NotBlank
    private String userLastName;
    @Email
    private String userEmail;
    @NotBlank
    @Size(min = 6, max = 20)
    private String userPassword;
    private Boolean isAdmin;


    @OneToOne(mappedBy = "user")
    private ShoppingBasket shoppingBasket;
}