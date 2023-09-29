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
    private String userFirstName;
    private String userLastName;
    @Email
    private String userEmail;
    private List<String> roles;
    private boolean isEnabled;
}
