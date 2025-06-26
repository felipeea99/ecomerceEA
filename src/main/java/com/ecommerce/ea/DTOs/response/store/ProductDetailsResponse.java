package com.ecommerce.ea.DTOs.response.store;

import com.ecommerce.ea.entities.store.Photo;
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
