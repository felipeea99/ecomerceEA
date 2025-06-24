package com.ecommerce.ea.controllers.store;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.DTOs.request.store.PriceBySizeRequest;
import com.ecommerce.ea.DTOs.request.store.ProductRequest;
import com.ecommerce.ea.DTOs.response.store.ProductResponse;
import com.ecommerce.ea.DTOs.update.ProductUpdate;
import com.ecommerce.ea.entities.store.PriceBySize;
import com.ecommerce.ea.services.store.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /// Adds a Product Object
    @ValidateStoreAccess
    @PostMapping("/{storeName}/")
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest, @RequestBody List<PriceBySizeRequest>priceBySizeList){
        //Sets the StoreId base on the StoreName from the Url
        productRequest.setStoreId(StoreContextHolder.getStoreId());
       return productService.addProduct(productRequest,priceBySizeList);
    }

    /// Edits a Product Object
    @PutMapping("/{storeName}/")
    public ProductResponse editProduct(@RequestBody ProductUpdate productUpdate){
        return productService.editProduct(productUpdate);
    }

    /// Deletes a Product Object
    @PostMapping("/{storeName}/{productId}")
    public Boolean deleteProduct(@PathVariable int productId){
        return productService.deleteProduct(productId);
    }

    /// This one is a call just for admin role
    @GetMapping("/adminProducts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{storeName}/{productId}")
    public ProductResponse findProductById(@PathVariable int productId){
        return productService.findProductById(productId);
    }

    /// This is the EndPoint that calls all the product referring to a certain store
    @ValidateStoreAccess
    @GetMapping("/{storeName}/")
    public List<ProductResponse> getProductsByStoreId(){
        UUID storeId = StoreContextHolder.getStoreId();
        return productService.getProductsByStoreId(storeId);
    }

    @ValidateStoreAccess
    @GetMapping("/{storeName}/productListExcel")
    public ByteArrayOutputStream listProductsExcel(){
        UUID storeId = StoreContextHolder.getStoreId();
        return productService.listProductsExcel(storeId);
    }
}
