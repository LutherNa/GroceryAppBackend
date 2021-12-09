package com.revature.vanqapp.repository;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.GroceryListProduct;
import com.revature.vanqapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface GroceryListProductRepository extends JpaRepository<GroceryListProduct, Integer> {
    List<GroceryListProduct> findByGroceryList(GroceryList groceryList);
    GroceryListProduct findByGroceryListAndProduct(GroceryList groceryList, Product product);
    @Transactional
    List<GroceryListProduct> deleteAllByGroceryList(GroceryList groceryList);
    @Transactional
    Integer deleteByGroceryListAndProduct(GroceryList groceryList, Product product);
}