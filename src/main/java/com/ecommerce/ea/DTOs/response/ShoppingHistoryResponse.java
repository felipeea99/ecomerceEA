package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.Product;
import com.ecommerce.ea.entities.ShoppingHistory;
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
    private Product productId;
    private Customer customerId;
    private String paymentProviderId;

    private static ShoppingHistoryResponse ToShoppingHistoryResponse(ShoppingHistory shoppingHistory){
        return new ShoppingHistoryResponse(
                shoppingHistory.getHistoryId(),
                shoppingHistory.getDateTime(),
                shoppingHistory.getQuantity(),
                shoppingHistory.getStatus(),
                shoppingHistory.getPurchaseUUID(),
                shoppingHistory.getProduct(),
                shoppingHistory.getCustomer(),
                shoppingHistory.getPaymentProviderId()
        );
    }

}

