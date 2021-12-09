package com.revature.vanqapp.repository;

import com.revature.vanqapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Product.ProductLocationId> {
    @Transactional
    Product deleteByProductIdAndLocationId(String productId, String locationId);
}