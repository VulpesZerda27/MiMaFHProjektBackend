package com.mima.mimafhprojektbackend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class MyUser {
    @Id
    @GeneratedValue
    private Long userId;
    @NotBlank
    private String userFirstName;
    @NotBlank
    private String userLastName;
    @NotBlank
    @Email
    private String userEmail;
    private boolean isEnabled = true;
    @NotBlank
    //@Size(min = 6, max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_basket_id", referencedColumnName = "shoppingBasketId")
    private ShoppingBasket shoppingBasket;
}