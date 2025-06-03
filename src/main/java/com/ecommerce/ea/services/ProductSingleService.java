package com.ecommerce.ea.services;


import com.ecommerce.ea.DTOs.request.ProductSinglePriceRequest;
import com.ecommerce.ea.DTOs.response.ProductResponse;
import com.ecommerce.ea.DTOs.response.ProductSinglePriceResponse;
import com.ecommerce.ea.DTOs.update.ProductSinglePriceUpdate;
import com.ecommerce.ea.entities.*;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IProductSingle;
import com.ecommerce.ea.repository.CategoryRepository;
import com.ecommerce.ea.repository.ProductSingleRepository;
import com.ecommerce.ea.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@Service
public class ProductSingleService implements IProductSingle {

    private final ProductSingleRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductSingleService(ProductSingleRepository productRepository, StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Mono<ProductSinglePriceResponse> AddProduct(ProductSinglePriceRequest productSinglePriceRequest) {
       //Get the objects
        Mono<Store> storeMono = storeRepository.findById(productSinglePriceRequest.getStoreId());
        Mono<Category> categoryMono = categoryRepository.findById(productSinglePriceRequest.getCategoryId());

        return Mono.zip(storeMono, categoryMono)
                .flatMap(tuple -> {
                    //Make the Tuple
                    Store store = tuple.getT1();
                    Category category = tuple.getT2();
                    //Create the ProductSinglePrice obj
                    ProductSinglePrice product = new ProductSinglePrice();
                    product.setActive(productSinglePriceRequest.isActive());
                    product.setStore(store);
                    product.setCategory(category);
                    product.setProductName(productSinglePriceRequest.getProductName());
                    product.setPrice(productSinglePriceRequest.getPrice());

                    // Save the product
                    return productRepository.save(product)
                            .map(savedProduct -> {
                                ProductSinglePriceResponse response = new ProductSinglePriceResponse();
                                response.setProductId(savedProduct.getProductId());
                                response.setProductName(savedProduct.getProductName());
                                response.setPrice(savedProduct.getPrice());
                                response.setStoreId(savedProduct.getStore().getStoreID());
                                response.setCategoryId(savedProduct.getCategory().getCategoryId());
                                response.setActive(savedProduct.isActive());
                                return response;
                            });
                });
    }

    @Override
    public Mono<ProductSinglePriceResponse> EditProduct(ProductSinglePriceUpdate productSinglePriceUpdate) {
        //Get the original object from the database
        Mono<ProductSinglePrice> product = productRepository.findById(productSinglePriceUpdate.getProductId());
        //Edit Changes


        return null;
    }

    @Override
    public Mono<Boolean> DeleteProduct(int productId) {
        return  productRepository.deleteById(productId).thenReturn(true).onErrorReturn(false);
    }

    @Override
    public Mono<List<ProductSinglePriceResponse>> GetAllProducts() {
        return productRepository.findAll()
                .flatMap(this::ToProductSinglePriceResponse)
                .collectList();
    }

    @Override
    public Mono<ProductSinglePriceResponse> GetProductById(int productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new BadRequestException("productId not found on the database " + productId)))
                .flatMap(this::ToProductSinglePriceResponse);
    }


    @Override
    public Mono<ProductSinglePriceResponse> GetProductByStoreId(UUID storeId) {
        return null;
    }

    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductsByCategoryId(int categoryId) {
        return null;
    }

    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductsRandomByCategory(int categoryId) {
        return null;
    }

    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductWithSizes() {
        return null;
    }

    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductByQuery(String query) {
        return null;
    }

    @Override
    public Mono<ByteArrayOutputStream> ListProductsExcel() {
        return null;
    }

    /// Product Convertions

    @Override
    public Mono<ProductSinglePrice> ToProductSinglePrice(ProductSinglePriceRequest requestObj) {
        //Get the category and Store object
        Mono<Category> categoryMono = categoryRepository.findById(requestObj.getCategoryId());
        Mono<Store> storeMono = storeRepository.findById(requestObj.getStoreId());

        return Mono.zip(categoryMono, storeMono)
                .flatMap(tuple -> {
                    Category category = tuple.getT1();
                    Store store = tuple.getT2();

                    ProductSinglePrice productS = new ProductSinglePrice();
                    productS.setCategory(category);
                    productS.setStore(store);
                    productS.setProductName(requestObj.getProductName());
                    productS.setActive(requestObj.isActive());
                    productS.setPrice(requestObj.getPrice());

                    return productRepository.save(productS);
                });
    }


    @Override
    public Mono<ProductSinglePriceResponse> ToProductSinglePriceResponse(ProductSinglePrice productObj) {

        return productRepository.findById(productObj.getProductId())
                .map(product -> {
                    ProductSinglePriceResponse productSR = new ProductSinglePriceResponse();
                    productSR.setProductId(productObj.getProductId());
                    productSR.setActive(productObj.isActive());
                    productSR.setProductName(productObj.getProductName());
                    productSR.setPrice(productObj.getPrice());

                    if (productObj.getStore() != null) {
                        productSR.setStoreId(productObj.getStore().getStoreID());
                    }
                    return productSR;
                });
    }

}
