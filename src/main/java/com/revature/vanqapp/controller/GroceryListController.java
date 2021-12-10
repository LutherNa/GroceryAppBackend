package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.GroceryListProduct;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.service.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController()
@RequestMapping(value = "/grocerylist")
public class GroceryListController {

    @Autowired
    private GroceryListService groceryListService;

    @GetMapping
    public List<GroceryList> getAllGroceryList(@RequestBody User user){
        return groceryListService.getAllGroceryList(user);
    }

    @GetMapping("/{name}/{locationId}/{userId}")
    public List<GroceryListProduct> viewGroceryList (@PathVariable String name, @PathVariable String userId){
        return groceryListService.viewGroceryList(name, Integer.parseInt(userId));
    }

    @PostMapping("/{name}/{locationId}/{userId}")
    public GroceryList createGroceryList (@PathVariable String name, @PathVariable String locationId, @PathVariable String userId){
        return groceryListService.createGroceryList(name, locationId, Integer.parseInt(userId));
    }

    @PostMapping("/{name}/{locationId}/{userId}/{productId}/{count}")
    public GroceryListProduct addProductToGroceryList (@PathVariable String name, @PathVariable String locationId, @PathVariable String userId, @PathVariable String productId, @PathVariable String count) throws IOException {
        HashMap<ProductFilterTerms,String> searchMap = new HashMap<>();
        searchMap.put(ProductFilterTerms.locationId, locationId);
        searchMap.put(ProductFilterTerms.productId, productId);
        return groceryListService.addProductToGroceryList(name, Integer.parseInt(userId), Integer.parseInt(count),  searchMap);
    }

    @DeleteMapping("/{name}/{locationId}/{userId}")
    public List<GroceryListProduct> deleteGroceryList (@PathVariable String name, @PathVariable String userId){
        return groceryListService.deleteGroceryList(name, Integer.parseInt(userId));
    }

    @DeleteMapping("/{name}/{locationId}/{userId}/{productId}")
    public GroceryListProduct deleteProductFromGroceryList (@PathVariable String name, @PathVariable String locationId, @PathVariable String userId, @PathVariable String productId) throws IOException {
        HashMap<ProductFilterTerms,String> searchMap = new HashMap<>();
        searchMap.put(ProductFilterTerms.locationId, locationId);
        searchMap.put(ProductFilterTerms.productId, productId);
        return groceryListService.deleteProductFromGroceryList(name, Integer.parseInt(userId), searchMap);
    }

}