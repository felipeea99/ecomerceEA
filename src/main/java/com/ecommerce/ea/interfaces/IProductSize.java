package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.ProductSize;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductSize {
    CompletableFuture<ProductSize> AddProductSize(ProductSize productSize);
    CompletableFuture<ProductSize> EditProductSize(ProductSize productSize);
    CompletableFuture<Boolean> DeleteProductSize(int productSizeID);
    CompletableFuture<List<ProductSize>> GetAllProductSizes();
    CompletableFuture<ProductSize> GetProductSizeById(int productSizeID);
    CompletableFuture<List<ProductSize>> GetProductSizesByProductId(int productId);
}
