package com.ecommerce.ea.controllers;

import com.ecommerce.ea.services.ProductSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductSingleService productService;

    @Autowired
    public ProductController(ProductSingleService productService) {
        this.productService = productService;
    }

//    @PostMapping("/addProduct")
//    public Product addProduct(@RequestBody Product product){
//        productService.AddProduct(product);
//        return product;
//    }

    @GetMapping("/read")
    public String read(){
        return "o";
    }



}
