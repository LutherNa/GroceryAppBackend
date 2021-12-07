package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.product.Product;
import com.revature.vanqapp.model.product.ProductFilterTerms;
import com.revature.vanqapp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.revature.vanqapp.NotAKrogerApp.tokenPool;

@RestController()
@RequestMapping(value = "/products")
public class ProductController {

    ProductService productService;

    public ProductController(){
        try{
            productService = new ProductService(tokenPool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping
    public List<Product> getProduct(@RequestBody HashMap<ProductFilterTerms, String> search) throws IOException {
        System.out.println(search);
        return productService.getProducts(search);
    }

    @PostMapping("/{productId}") // -> localhost:8081/products/<some_productId>
    public List<Product> getProductById(@PathVariable String productId, @RequestBody HashMap<ProductFilterTerms,String> search) throws IOException {
        search.put(ProductFilterTerms.productId, productId);
        return productService.getProducts(search);
    }
}
