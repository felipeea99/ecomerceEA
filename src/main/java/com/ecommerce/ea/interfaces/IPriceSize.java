package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.response.PriceBySizeResponse;
import com.ecommerce.ea.DTOs.update.PriceBySizeUpdate;
import com.ecommerce.ea.entities.PriceBySize;

import java.util.List;

public interface IPriceSize {
    List<PriceBySizeResponse> addProductSize(List<PriceBySizeRequest> request);
    PriceBySizeResponse editProductSize(PriceBySizeUpdate update);
    Boolean deleteProductSize(int priceSizeId);
    List<PriceBySizeResponse> getAllProductSizes();
    PriceBySizeResponse getPriceSizeById(int priceSizeId);
    List<PriceBySizeResponse> getProductSizesByProductId(int productId);
    PriceBySizeResponse ToPriceBySizeResponse(PriceBySize priceBySize);
    PriceBySize ToPriceBySizeObj(PriceBySizeRequest request);


    }
