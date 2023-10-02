package com.mima.mimafhprojektbackend.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class MyUser {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    private boolean isEnabled;
    @NotBlank
    //@Size(min = 6, max = 20)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ElementCollection
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    private List<String> roles;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_basket_id")
    private ShoppingBasket shoppingBasket;
}