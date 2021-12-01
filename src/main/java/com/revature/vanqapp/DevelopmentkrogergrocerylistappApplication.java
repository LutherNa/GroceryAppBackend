package com.revature.vanqapp;

import com.revature.vanqapp.model.FilterTerms;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.service.ProductService;
import com.revature.vanqapp.service.TokenService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DevelopmentkrogergrocerylistappApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(DevelopmentkrogergrocerylistappApplication.class, args);
		ProductService productService = new ProductService();
		HashMap<FilterTerms,String> searchTest = new HashMap<>();
		searchTest.put(FilterTerms.locationId,"01400943");
		searchTest.put(FilterTerms.fulfillment,"ais");
		searchTest.put(FilterTerms.term,"cereal");
		List<Product> products = productService.getProducts(searchTest);
		for (Product product : products) {
			System.out.println(product.getDescription()+"\n"+product.getProductId());
			System.out.println(productService.verifyProductById(product.getProductId()));
		}
	}

}
