package com.revature.vanqapp;

import com.revature.vanqapp.model.*;
import com.revature.vanqapp.service.GroceryListService;
import com.revature.vanqapp.service.LocationService;
import com.revature.vanqapp.service.ProductService;
import lombok.SneakyThrows;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class NotAKrogerApp {

    public static GenericObjectPool<AuthToken> tokenPool;

    @SneakyThrows
    public static void main(String[] args) {
//        ConfigTokenPool();
//        TestOnRun();
        SpringApplication.run(NotAKrogerApp.class, args);
    }

//    public static void ConfigTokenPool() {
//        tokenPool = new GenericObjectPool<>(new AuthTokenFactoryBean.AuthTokenFactory());
//        GenericObjectPoolConfig<AuthToken> tokenPoolConfig = new GenericObjectPoolConfig<>();
//        tokenPoolConfig.setMaxTotal(1);
//        tokenPool.setConfig(tokenPoolConfig);
//    }

//    private static void TestOnRun() {
//        ProductService productService = new ProductService(tokenPool);
//        GroceryListService groceryListService;
//        try {
//            groceryListService = new GroceryListService(tokenPool);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        LocationService locationService = new LocationService(tokenPool);
//        HashMap<ProductFilterTerms,String> searchTest = new HashMap<>();
//        searchTest.put(ProductFilterTerms.locationId,"01400943");
//        searchTest.put(ProductFilterTerms.term,"cereal");
//        List<Product> products = null;
//        try {
//            products = productService.getProducts(searchTest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (Product product : products) {
//            System.out.println(product.getDescription()+"\n"+product.getProductId()+"\n"+product.getAisleLocations() +
//                    "\n" + NumberFormat.getCurrencyInstance(Locale.US).format(product.getRegularPrice()));
//        }
//        HashMap<LocationFilterTerms,String> searchLocationTest = new HashMap<>();
//        searchLocationTest.put(LocationFilterTerms.zipCode, "37601");
//        List<Location> locations = null;
//        try {
//            locations = locationService.getLocations(searchLocationTest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for(Location location: locations){
//            System.out.println(location);
//        }
//        HashMap<ProductDetailsFilterTerms,String> searchProductInformationTest = new HashMap<>();
//        searchProductInformationTest.put(ProductDetailsFilterTerms.productId, "0088491201425");
//        searchProductInformationTest.put(ProductDetailsFilterTerms.locationId, "01400943");
//        System.out.println(groceryListService.getProductInformation(searchProductInformationTest));
//    }
}