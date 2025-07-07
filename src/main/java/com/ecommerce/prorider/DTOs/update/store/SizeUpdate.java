package com.ecommerce.prorider.DTOs.update.store;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeUpdate {
    private int sizeId;

    @NotNull(message = "size is mandatory")
    private String size;
}
