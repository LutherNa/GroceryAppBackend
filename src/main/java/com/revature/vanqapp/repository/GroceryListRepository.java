package com.revature.vanqapp.repository;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroceryListRepository extends JpaRepository<GroceryList, Integer> {
    List<GroceryList> findByOwner(User user);
    Optional<GroceryList> findByOwnerAndListName(User user, String listName);
}