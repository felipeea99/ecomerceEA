package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.ProductMultiPriceRequest;
import com.ecommerce.ea.DTOs.request.ProductSinglePriceRequest;
import com.ecommerce.ea.DTOs.response.ProductMultiPriceResponse;
import com.ecommerce.ea.DTOs.response.ProductResponse;
import com.ecommerce.ea.DTOs.response.ProductSinglePriceResponse;
import com.ecommerce.ea.DTOs.update.ProductMultiPriceUpdate;
import com.ecommerce.ea.DTOs.update.ProductSinglePriceUpdate;
import com.ecommerce.ea.entities.ProductMultiPrice;
import com.ecommerce.ea.entities.ProductSinglePrice;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public interface IProductMulti {

    //Product Multi Price Methods
    Mono<ProductMultiPriceResponse> AddProduct(ProductMultiPriceRequest productMultiPriceRequest);
    Mono<ProductMultiPriceResponse> EditProduct(ProductMultiPriceUpdate productMultiPriceUpdate);

    //Delete
    Mono<Boolean> DeleteProduct(int productId);

    //Other Methods
    Mono<List<ProductResponse>> GetAllProducts();
    Mono<ProductResponse> GetProductById(int productId);
    Mono<ProductResponse> GetProductByStoreId(UUID storeId);
    Mono<List<ProductResponse>> GetProductsByCategoryId(int categoryId);
    Mono<List<ProductResponse>> GetProductsRandomByCategory(int categoryId);
    Mono<List<ProductResponse>> GetProductWithSizes();
    Mono<List<ProductResponse>> GetProductByQuery(String query);
    Mono<ByteArrayOutputStream> ListProductsExcel();

    //Mapping Product Single Price
    Mono<ProductMultiPrice>ToProductMultiPrice(ProductMultiPriceRequest productMultiPriceRequest);
    Mono<ProductMultiPriceResponse>ToProductMultiPriceResponse(ProductMultiPrice ProductMultiPrice);
}
