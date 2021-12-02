package com.revature.vanqapp;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.Location;
import com.revature.vanqapp.model.LocationFilterTerms;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.service.LocationService;
import com.revature.vanqapp.service.ProductService;
import com.revature.vanqapp.util.AuthTokenFactory;
import lombok.SneakyThrows;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class NotAKrogerApp {

    public static GenericObjectPool<AuthToken> tokenPool;

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(NotAKrogerApp.class, args);
        ConfigTokenPool();
        ProductService productService = new ProductService(tokenPool);
        LocationService locationService = new LocationService();
        HashMap<ProductFilterTerms,String> searchTest = new HashMap<>();
        searchTest.put(ProductFilterTerms.locationId,"01400943");
        searchTest.put(ProductFilterTerms.fulfillment,"ais");
        searchTest.put(ProductFilterTerms.term,"cereal");
        List<Product> products = productService.getProducts(searchTest);
        for (Product product : products) {
            System.out.println(product.getDescription()+"\n"+product.getProductId());
            System.out.println(productService.verifyProductById(product.getProductId()));
        }
        HashMap<LocationFilterTerms,String> searchLocationTest = new HashMap<>();
        searchLocationTest.put(LocationFilterTerms.zipCode, "37601");
        List<Location> locations = locationService.getLocations(searchLocationTest);
        for(Location location: locations){
            System.out.println(location);
        }
    }

    public static void ConfigTokenPool() {
        tokenPool = new GenericObjectPool<>(new AuthTokenFactory());
        GenericObjectPoolConfig<AuthToken> tokenPoolConfig = new GenericObjectPoolConfig<>();
        tokenPoolConfig.setMaxTotal(1);
        tokenPool.setConfig(tokenPoolConfig);
    }
}