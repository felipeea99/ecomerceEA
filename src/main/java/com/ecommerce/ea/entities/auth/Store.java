package com.ecommerce.ea.entities.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID storeId;
    @NotBlank
    private String storeName;
    @NotBlank
    private String domain;
    @NotBlank
    @Email
    private String storeEmail;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserAcc user;
    @Column(name = "stripe_account_id")
    private String stripeAccountId;
    @Column(nullable = false)
    private boolean onboardingCompleted = false;
    @Column(nullable = false)
    private boolean enable = false;

}
