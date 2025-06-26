package com.ecommerce.ea.DTOs.response.store;

import com.ecommerce.ea.entities.store.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SizeResponse {
    private int sizeId;
    private String size;
}
