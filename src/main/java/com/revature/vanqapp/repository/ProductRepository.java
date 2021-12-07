package com.revature.vanqapp.repository;

import com.revature.vanqapp.model.product.Product;
import com.revature.vanqapp.model.product.ProductLocationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductLocationId> {
}