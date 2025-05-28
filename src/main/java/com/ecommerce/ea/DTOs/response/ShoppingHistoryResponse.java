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
public class ShoppingHistoryResponse {
    private int historyId;
    private Date dateTime;
    private int quantity;
    private String status;
    private String purchaseUUID;
    private ProductResponse product;
    private CustomerResponse customer;
    private String paymentProviderId;
}

