package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID customerId;
    @NotNull
    @OneToOne
    @JoinColumn(name = "userId")
    private UserAcc user;
    @OneToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
