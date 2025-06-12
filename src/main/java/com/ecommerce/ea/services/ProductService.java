package com.ecommerce.ea.services;


import com.ecommerce.ea.DTOs.response.ProductSinglePriceResponse;
import com.ecommerce.ea.entities.*;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IProduct;
import com.ecommerce.ea.repository.CategoryRepository;
import com.ecommerce.ea.repository.ProductRepository;
import com.ecommerce.ea.repository.SizeRepository;
import com.ecommerce.ea.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ProductSingleService implements IProduct {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private  final SizeRepository sizeRepository;

    @Autowired
    public ProductSingleService(ProductRepository productRepository, StoreRepository storeRepository, CategoryRepository categoryRepository, SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
    }

    ///Add ProductSingle Objects
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

    ///Edit ProductSingle Objects
    @Override
    public Mono<ProductSinglePriceResponse> EditProduct(ProductSinglePriceUpdate productUpdate) {
        //Edit Changes
        return productRepository.findById(productUpdate.getProductId())
                .switchIfEmpty(Mono.error(new BadRequestException("productId was not found on the database")))
                .flatMap(product ->{
                    product.setCategory(productUpdate.getCategoryId());
                    product.setProductName(productUpdate.getProductName());
                    product.setActive(productUpdate.isActive());
                    product.setPrice(productUpdate.getPrice());
                    return productRepository.save(product);
                })
                .flatMap(this::ToProductSinglePriceResponse);
    }
    ///Delete ProductSingle Objects
    @Override
    public Mono<Boolean> DeleteProduct(int productId) {
        return  productRepository.deleteById(productId).thenReturn(true).onErrorReturn(false);
    }

    ///Retrieve All ProductSingle Objects for Admin Calls
    @Override
    public Mono<List<ProductSinglePriceResponse>> GetAllProducts() {
        return productRepository.findAll()
                .flatMap(this::ToProductSinglePriceResponse)
                .collectList();
    }


    /// Retrieve a ProductSingle object by productId
    @Override
    public Mono<ProductSinglePriceResponse> GetProductById(int productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new BadRequestException("productId not found on the database " + productId)))
                .flatMap(this::ToProductSinglePriceResponse);
    }

    /// Retrieve products base on the storeId
    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductsByStoreId(UUID storeId) {
        return productRepository.GetAllProductsByStoreId(storeId)
                .switchIfEmpty(Mono.error(new BadRequestException("storeId not found on the database " + storeId)))
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::ToProductSinglePriceResponse)
                .collectList();
    }

    /// Retrieve the productSingle objects base on the categoryId and the storeId
    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductsByCategoryId(int categoryId, UUID storeId) {
        return productRepository.GetAllProductsByStoreId(storeId)
                .flatMapMany(Flux::fromIterable) //Make the Flux and Call fromIterable
                .filter(product -> product.getCategory().getCategoryId() == categoryId)
                .flatMap(this::ToProductSinglePriceResponse)
                .collectList();
    }

    /// Retrieve the productSingle objects base on the categoryId and the storeId and Mix them to have random order
    @Override
    public Mono<List<ProductSinglePriceResponse>> GetProductsRandomByCategory(int categoryId,  UUID storeId) {
        return productRepository.GetAllProductsByStoreId(storeId)
                .flatMapMany(Flux::fromIterable)
                .filter(product -> product.getCategory().getCategoryId() == categoryId)
                .flatMap(this::ToProductSinglePriceResponse)
                .collectList()
                .map(list ->{
                    Collections.shuffle(list); //Mix List
                    return list;
                });
    }


    @Override
    public Mono<ByteArrayOutputStream> ListProductsExcel() {
        return null;
    }

    /// Product Convertions || ProductRequest to ProductObject
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

    /// Product Convertions || ProductObject to ProductResponse
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
