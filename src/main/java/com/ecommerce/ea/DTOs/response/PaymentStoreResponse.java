package com.ecommerce.ea.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStoreResponse {
    private int storePaidId;
    private Date date;
    private int storeId;
    private double amount;
    private int referencePayment;
}
