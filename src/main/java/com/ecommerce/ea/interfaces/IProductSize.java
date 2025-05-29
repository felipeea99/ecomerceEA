package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.ProductSizeRequest;
import com.ecommerce.ea.DTOs.response.ProductSizeResponse;
import com.ecommerce.ea.DTOs.update.ProductSizeUpdate;
import com.ecommerce.ea.entities.ProductSize;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductSize {
    CompletableFuture<ProductSizeResponse> AddProductSize(ProductSizeRequest productSizeRequest);
    CompletableFuture<ProductSizeResponse> EditProductSize(ProductSizeUpdate productSizeUpdate);
    CompletableFuture<Boolean> DeleteProductSize(int productSizeID);
    CompletableFuture<List<ProductSizeResponse>> GetAllProductSizes();
    CompletableFuture<ProductSizeResponse> GetProductSizeById(int productSizeID);
    CompletableFuture<List<ProductSizeResponse>> GetProductSizesByProductId(int productId);
}
