package com.ecommerce.prorider.DTOs.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsResponse {
    private ProductResponse productResponse;
    private List<PhotoResponse> photoList;
    private List<PriceBySizeResponse> priceBySizeResponseList;
}
