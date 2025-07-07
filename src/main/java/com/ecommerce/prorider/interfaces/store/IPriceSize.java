package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.prorider.DTOs.response.store.PriceBySizeResponse;
import com.ecommerce.prorider.DTOs.update.store.PriceBySizeUpdate;
import com.ecommerce.prorider.entities.store.PriceBySize;

import java.util.List;
import java.util.UUID;

public interface IPriceSize {
    List<PriceBySizeResponse> addProductSize(List<PriceBySizeRequest> request);
    PriceBySizeResponse editProductSize(PriceBySizeUpdate update);
    Boolean deleteProductSize(int priceSizeId);
    List<PriceBySizeResponse> findAllProductSizes();
    List<PriceBySize> findAllProductSizesBaseForm();
    PriceBySizeResponse findPriceSizeById(int priceSizeId);
    List<PriceBySizeResponse> findProductSizesByProductId(int productId);
    List<PriceBySizeResponse> findAllPriceBySizeByStoreId(UUID storeId);
    PriceBySizeResponse ToPriceBySizeResponse(PriceBySize priceBySize);
    PriceBySize ToPriceBySizeObj(PriceBySizeRequest request);


    }
