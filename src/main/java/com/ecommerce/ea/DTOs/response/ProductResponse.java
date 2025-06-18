package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.PriceBySize;
import com.ecommerce.ea.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private int productId;
    private String productName;
    private boolean isActive;
    private UUID storeId;
    private int categoryId;
    private boolean hasSizes;
    private double price;
    private List<PriceBySize> pricesBySize = new ArrayList<>();

    public static ProductResponse ToProductResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setActive(product.isActive());
        response.setStoreId(product.getStore().getStoreId());
        response.setCategoryId(product.getCategory().getCategoryId());
        response.setHasSizes(product.isHasSizes());
        response.setPrice(product.getPrice());
        response.setPricesBySize(product.getPricesBySize());

        return response;
    }
}
