package com.mima.mimafhprojektbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mima.mimafhprojektbackend.model.ShoppingBasket;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class MyUserDTO {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private List<String> roles;
    private Boolean enabled;
}
