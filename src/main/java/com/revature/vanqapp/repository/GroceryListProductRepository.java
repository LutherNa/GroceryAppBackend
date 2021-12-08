package com.revature.vanqapp.repository;

import com.revature.vanqapp.model.GroceryListProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroceryListProductRepository extends JpaRepository<GroceryListProduct, Integer> {
}