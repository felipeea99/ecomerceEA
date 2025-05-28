package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStoreRequest {
    @NotNull(message = "date is mandatory")
    private Date date;

    @NotNull(message = "storeId is mandatory")
    private int storeId;

    @Min(value = 0, message = "amount must be positive or zero")
    private double amount;

    @NotNull(message = "referencePayment is mandatory")
    @Size(min = 1, message = "referencePayment cannot be empty")
    private String referencePayment;
}
