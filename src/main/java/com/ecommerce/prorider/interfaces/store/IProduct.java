package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.prorider.DTOs.request.store.ProductRequest;
import com.ecommerce.prorider.DTOs.response.store.ProductDetailsResponse;
import com.ecommerce.prorider.DTOs.response.store.ProductResponse;
import com.ecommerce.prorider.DTOs.update.store.ProductUpdate;
import com.ecommerce.prorider.entities.store.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IProduct {
    ProductResponse addProduct(ProductRequest productRequest,List<PriceBySizeRequest>priceBySizeList);
    ProductResponse editProduct(ProductUpdate productUpdate);
    Boolean deleteProduct(int productId);
    List<ProductResponse> getAllProducts();
    ProductResponse findProductById(int productId);
    Product findProductByIdBaseForm(int productId);
    List<ProductResponse> getProductsByStoreId(UUID storeId);
    List<ProductResponse> getProductsByCategoryId(int categoryId, UUID storeId);
    List<ProductResponse> getProductsRandomByCategory(int categoryId, UUID storeId);
    ProductDetailsResponse findProductDetailsByProductId(int productId);
    List<ProductDetailsResponse> findProductDetailsByStoreId(UUID storeId);
    ByteArrayOutputStream listProductsExcel(UUID storeId);
    ProductResponse ToProductResponse(Product product);
}
