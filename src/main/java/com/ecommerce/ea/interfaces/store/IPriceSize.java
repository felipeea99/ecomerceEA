package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.response.store.PriceBySizeResponse;
import com.ecommerce.ea.DTOs.update.PriceBySizeUpdate;
import com.ecommerce.ea.entities.store.PriceBySize;

import java.util.List;

public interface IPriceSize {
    List<PriceBySizeResponse> addProductSize(List<PriceBySizeRequest> request);
    PriceBySizeResponse editProductSize(PriceBySizeUpdate update);
    Boolean deleteProductSize(int priceSizeId);
    List<PriceBySizeResponse> findAllProductSizes();
    List<PriceBySize> findAllProductSizesBaseForm();
    PriceBySizeResponse getPriceSizeById(int priceSizeId);
    List<PriceBySizeResponse> getProductSizesByProductId(int productId);
    PriceBySizeResponse ToPriceBySizeResponse(PriceBySize priceBySize);
    PriceBySize ToPriceBySizeObj(PriceBySizeRequest request);


    }
