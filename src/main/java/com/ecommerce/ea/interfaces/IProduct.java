package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.ProductRequest;
import com.ecommerce.ea.DTOs.response.ProductResponse;
import com.ecommerce.ea.DTOs.update.ProductUpdate;
import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.entities.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IProduct {
    ProductResponse addProduct(ProductRequest productRequest);
    ProductResponse editProduct(ProductUpdate productUpdate);
    Boolean deleteProduct(int productId);
    //Other Methods
    List<ProductResponse> getAllProducts();
    ProductResponse findProductById(int productId);
    Product findProductByIdBaseForm(int productId);
    List<ProductResponse> getProductsByStoreId(UUID storeId);
    List<ProductResponse> getProductsByCategoryId(int categoryId, UUID storeId);
    List<ProductResponse> getProductsRandomByCategory(int categoryId, UUID storeId);
    ByteArrayOutputStream listProductsExcel(UUID storeId);

}
