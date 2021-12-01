package com.revature.vanqapp;

import com.revature.vanqapp.service.ProductService;
import com.revature.vanqapp.service.TokenService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevelopmentkrogergrocerylistappApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(DevelopmentkrogergrocerylistappApplication.class, args);
		ProductService productService = new ProductService();
		System.out.println(productService.getProducts());
	}

}
