package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storePaidId;
    @NotNull
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @NotNull
    @Min(0)
    private double amount;
    @NotNull
    private String referencePayment;

}
