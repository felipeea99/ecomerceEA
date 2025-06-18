package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingHistoryResponse {
    private int historyId;
    private Date dateTime;
    private int quantity;
    private StatusType status;
    private String purchaseUUID;
    private int productId;
    private UUID customerId;
    private int addressId;


}

