package com.ecommerce.prorider.DTOs.request.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoRequest {
    @NotBlank(message = "photoValue is mandatory")
    private String photoValue;
    @NotNull(message = "productId is mandatory")
    private int productId;
    
}
