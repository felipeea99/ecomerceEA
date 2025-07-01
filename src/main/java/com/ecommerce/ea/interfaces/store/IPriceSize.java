package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.response.store.PriceBySizeResponse;
import com.ecommerce.ea.DTOs.update.store.PriceBySizeUpdate;
import com.ecommerce.ea.entities.store.PriceBySize;

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
