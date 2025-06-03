package com.ecommerce.ea.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseList {
    List<ProductSinglePriceResponse> productSinglePriceResponseList;
    List<ProductMultiPriceResponse> productMultiPriceResponses;
}
