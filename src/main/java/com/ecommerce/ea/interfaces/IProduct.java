package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.ProductRequest;
import com.ecommerce.ea.DTOs.response.ProductResponse;
import com.ecommerce.ea.DTOs.update.ProductUpdate;
import com.ecommerce.ea.entities.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IProduct {
    CompletableFuture<ProductResponse> AddProduct(ProductRequest productRequest);
    CompletableFuture<ProductResponse> EditProduct(ProductUpdate productUpdate);
    CompletableFuture<Boolean> DeleteProduct(int productId);
    CompletableFuture<List<ProductResponse>> GetAllProducts();
    CompletableFuture<ProductResponse> GetProductById(int productId);
    CompletableFuture<ProductResponse> GetProductByStoreId(UUID storeId);
    CompletableFuture<List<ProductResponse>> GetProductsByCategoryId(int categoryId);
    CompletableFuture<List<ProductResponse>> GetProductsRandomByCategory(int categoryId);
    CompletableFuture<List<ProductResponse>> GetProductWithSizes();
    CompletableFuture<List<ProductResponse>> GetProductByQuery(String query);
    CompletableFuture<ByteArrayOutputStream> ListProductsExcel();
}
