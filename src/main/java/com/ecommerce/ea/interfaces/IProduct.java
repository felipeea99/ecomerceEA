package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.response.ProductSinglePriceResponse;
import com.ecommerce.ea.entities.ProductSinglePrice;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IProductSingle {
    //Product Single Price Methods
    Mono<ProductSinglePriceResponse> AddProduct(ProductSinglePriceRequest productSinglePriceRequest);
    Mono<ProductSinglePriceResponse> EditProduct(ProductSinglePriceUpdate productSinglePriceUpdate);

    //Delete
    Mono<Boolean> DeleteProduct(int productId);

    //Other Methods
    Mono<List<ProductSinglePriceResponse>> GetAllProducts();
    Mono<ProductSinglePriceResponse> GetProductById(int productId);
    Mono<List<ProductSinglePriceResponse>> GetProductsByStoreId(UUID storeId);
    Mono<List<ProductSinglePriceResponse>> GetProductsByCategoryId(int categoryId, UUID storeId);
    Mono<List<ProductSinglePriceResponse>> GetProductsRandomByCategory(int categoryId, UUID storeId);
    Mono<ByteArrayOutputStream> ListProductsExcel();

    //Mapping Product Single Price
    Mono<ProductSinglePrice>ToProductSinglePrice(ProductSinglePriceRequest productSinglePriceRequest);
    Mono<ProductSinglePriceResponse>ToProductSinglePriceResponse(ProductSinglePrice productSinglePrice);

}
