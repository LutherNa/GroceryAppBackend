package com.revature.vanqapp.repository;

import com.revature.vanqapp.model.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroceryListProductRepository extends JpaRepository<GroceryList, Integer> {
}