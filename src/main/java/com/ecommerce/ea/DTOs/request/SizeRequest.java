package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeRequest {
    @NotNull(message = "size is mandatory")
    private String size;
}
