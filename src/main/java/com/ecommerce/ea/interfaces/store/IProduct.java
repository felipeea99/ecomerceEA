package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.request.store.ProductRequest;
import com.ecommerce.ea.DTOs.response.store.ProductResponse;
import com.ecommerce.ea.DTOs.update.ProductUpdate;
import com.ecommerce.ea.entities.store.Product;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IProduct {
    ProductResponse addProduct(ProductRequest productRequest,List<PriceBySizeRequest>priceBySizeList);
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
