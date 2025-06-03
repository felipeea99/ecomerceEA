package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.ProductMultiPriceRequest;
import com.ecommerce.ea.DTOs.response.ProductMultiPriceResponse;
import com.ecommerce.ea.DTOs.response.ProductResponse;
import com.ecommerce.ea.DTOs.update.ProductMultiPriceUpdate;
import com.ecommerce.ea.entities.Category;
import com.ecommerce.ea.entities.ProductMultiPrice;
import com.ecommerce.ea.entities.Store;
import com.ecommerce.ea.interfaces.IProductMulti;
import com.ecommerce.ea.repository.CategoryRepository;
import com.ecommerce.ea.repository.ProductMultiRepository;
import com.ecommerce.ea.repository.ProductSingleRepository;
import com.ecommerce.ea.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

public class ProductMultiService implements IProductMulti {

    private final ProductMultiRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductMultiService(ProductMultiRepository productRepository, StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Mono<ProductMultiPriceResponse> AddProduct(ProductMultiPriceRequest productMultiPriceRequest) {
        return null;
    }

    @Override
    public Mono<ProductMultiPriceResponse> EditProduct(ProductMultiPriceUpdate productMultiPriceUpdate) {
        return null;
    }

    @Override
    public Mono<Boolean> DeleteProduct(int productId) {
        return null;
    }

    @Override
    public Mono<List<ProductResponse>> GetAllProducts() {
        return null;
    }

    @Override
    public Mono<ProductResponse> GetProductById(int productId) {
        return null;
    }

    @Override
    public Mono<ProductResponse> GetProductByStoreId(UUID storeId) {
        return null;
    }

    @Override
    public Mono<List<ProductResponse>> GetProductsByCategoryId(int categoryId) {
        return null;
    }

    @Override
    public Mono<List<ProductResponse>> GetProductsRandomByCategory(int categoryId) {
        return null;
    }

    @Override
    public Mono<List<ProductResponse>> GetProductWithSizes() {
        return null;
    }

    @Override
    public Mono<List<ProductResponse>> GetProductByQuery(String query) {
        return null;
    }

    @Override
    public Mono<ByteArrayOutputStream> ListProductsExcel() {
        return null;
    }

    @Override
    public Mono<ProductMultiPrice> ToProductMultiPrice(ProductMultiPriceRequest requestObj) {
        //Get the category and Store object
        Mono<Category> categoryMono = categoryRepository.findById(requestObj.getCategoryId());
        Mono<Store> storeMono = storeRepository.findById(requestObj.getStoreId());

        return Mono.zip(categoryMono, storeMono)
                .flatMap(tuple -> {
                    Category category = tuple.getT1();
                    Store store = tuple.getT2();

                    ProductMultiPrice productS = new ProductMultiPrice();
                    productS.setCategory(category);
                    productS.setStore(store);
                    productS.setProductName(requestObj.getProductName());
                    productS.setActive(requestObj.isActive());
                    productS.setMultiPrice(requestObj.getMultiPrice());

                    return productRepository.save(productS);
                });
    }

    @Override
    public Mono<ProductMultiPriceResponse> ToProductMultiPriceResponse(ProductMultiPrice productObj) {
        return productRepository.findById(productObj.getProductId())
                .map(product -> {
                    ProductMultiPriceResponse productSR = new ProductMultiPriceResponse();
                    productSR.setProductId(productObj.getProductId());
                    productSR.setActive(productObj.isActive());
                    productSR.setProductName(productObj.getProductName());
                    productSR.setMultiPrice(productObj.isMultiPrice());

                    if (productObj.getStore() != null) {
                        productSR.setStoreId(productObj.getStore().getStoreID());
                    }
                    return productSR;
                });
    }
}
