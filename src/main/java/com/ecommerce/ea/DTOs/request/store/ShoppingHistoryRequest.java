package com.ecommerce.ea.DTOs.request.store;

import com.ecommerce.ea.entities.store.StatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class ShoppingHistoryRequest {
    @NotNull
    @PastOrPresent(message = "Date cannot be in the future")
    private Date dateTime;

    @Min(0)
    private int quantity;

    @NotBlank(message = "status is mandatory")
    private StatusType status;

    @NotBlank(message = "purchaseUUID is mandatory")
    private String purchaseUUID;

    @NotNull(message = "productId is mandatory")
    private int productId;

    @NotNull(message = "customerId is mandatory")
    private UUID customerId;

    @NotNull(message = "addressId is mandatory")
    private int addressId;

    @NotNull(message = "storeId is mandatory")
    private UUID storeId;


}
