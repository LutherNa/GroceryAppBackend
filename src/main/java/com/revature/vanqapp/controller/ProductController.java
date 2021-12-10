package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController @CrossOrigin("*")
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

//    public ProductController(){
//        try{
//            productService = new ProductService(tokenPool);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @PostMapping
    public List<Product> getProduct(
            @Parameter(schema = @Schema(implementation = ProductFilterTerms.ProductFilterTermsDummy.class))
            @RequestBody HashMap<ProductFilterTerms, String> search) throws IOException {
        System.out.println(search);
        return productService.getProducts(search);
    }

    @PostMapping("/{productId}") // -> localhost:8081/products/<some_productId>
    public List<Product> getProductById(
            @PathVariable String productId,

            @Parameter(schema = @Schema(implementation = ProductFilterTerms.ProductFilterTermsDummy.class))
            @RequestBody HashMap<ProductFilterTerms,String> search) throws IOException {
        search.put(ProductFilterTerms.productId, productId);
        return productService.getProducts(search);
    }
}
