package com.ecommerce.ea.services;


import com.ecommerce.ea.entities.Product;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IProduct;
import com.ecommerce.ea.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductService implements IProduct {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<Product> AddProduct(Product product) {
        Product productObj = this.productRepository.save(product);
        return CompletableFuture.completedFuture(productObj);
    }


    @Override
    public CompletableFuture<Product> EditProduct(Product product) {
        Product productObj = productRepository.findById(product.getProductId()).orElseThrow(() -> new BadRequestException("productId was not found on the database"));

        productObj.setProductName(product.getProductName());
        productObj.setCategory(product.getCategory());
        productObj.setStore(product.getStore());
        productObj.setActive(product.isActive());

        return null;
    }

    @Override
    public CompletableFuture<Boolean> DeleteProduct(int productId) {
        return null;
    }

    @Override
    public CompletableFuture<List<Product>> GetAllProducts() {
        return null;
    }

    @Override
    public CompletableFuture<Product> GetProductById(int productId) {
        return null;
    }

    @Override
    public CompletableFuture<Product> GetProductByStoreId(UUID storeId) {
        return null;
    }

    @Override
    public CompletableFuture<List<Product>> GetProductsByCategoryId(int categoryId) {
        return null;
    }

    @Override
    public CompletableFuture<List<Product>> GetProductsRandomByCategory(int categoryId) {
        return null;
    }

    @Override
    public CompletableFuture<List<Product>> GetProductWithSizes() {
        return null;
    }

    @Override
    public CompletableFuture<List<Product>> GetProductByQuery(String query) {
        return null;
    }

    @Override
    public CompletableFuture<ByteArrayOutputStream> ListProductsExcel() {
        return null;
    }
}
