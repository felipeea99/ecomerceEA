package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeID;
    @NotBlank
    private String storeName;
    @NotBlank
    @Email
    private String storeEmail;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAcc user;

}
