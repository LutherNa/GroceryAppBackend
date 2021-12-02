package com.revature.vanqapp;

import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.service.ProductService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class NotAKrogerApp {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(NotAKrogerApp.class, args);
        ProductService productService = new ProductService();
        HashMap<ProductFilterTerms,String> searchTest = new HashMap<>();
        searchTest.put(ProductFilterTerms.locationId,"01400943");
        searchTest.put(ProductFilterTerms.fulfillment,"ais");
        searchTest.put(ProductFilterTerms.term,"cereal");
        List<Product> products = productService.getProducts(searchTest);
        for (Product product : products) {
            System.out.println(product.getDescription()+"\n"+product.getProductId());
        }
    }

}