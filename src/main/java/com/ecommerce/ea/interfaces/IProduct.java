package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IProduct {
    CompletableFuture<Product> AddProduct(Product product);
    CompletableFuture<Product> EditProduct(Product product);
    CompletableFuture<Boolean> DeleteProduct(int productId);
    CompletableFuture<List<Product>> GetAllProducts();
    CompletableFuture<Product> GetProductById(int productId);
    CompletableFuture<Product> GetProductByStoreId(UUID storeId);
    CompletableFuture<List<Product>> GetProductsByCategoryId(int categoryId);
    CompletableFuture<List<Product>> GetProductsRandomByCategory(int categoryId);
    CompletableFuture<List<Product>> GetProductWithSizes();
    CompletableFuture<List<Product>> GetProductByQuery(String query);
    CompletableFuture<ByteArrayOutputStream> ListProductsExcel();
}
