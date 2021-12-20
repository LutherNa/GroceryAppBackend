package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.GroceryListProduct;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.service.GroceryListService;
import com.revature.vanqapp.service.UserService;
import com.revature.vanqapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController @CrossOrigin("*")
@RequestMapping(value = "/grocerylist")
public class GroceryListController {

    @Autowired
    private GroceryListService groceryListService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    private User parseToken(String token) {
        return userService.loadUserByUsername(jwtUtil.extractUsername(token.replace("Bearer","").trim()));
    }

    @GetMapping
    public List<GroceryList> getAllGroceryList(@RequestHeader("Authorization") String token) {
        return groceryListService.getAllGroceryList(parseToken(token));
    }

    @GetMapping("/{name}/{locationId}")
    public List<GroceryListProduct> viewGroceryList (@PathVariable String name, @RequestHeader("Authorization") String token){
        return groceryListService.viewGroceryList(name, parseToken(token).getUserId());
    }

    @PostMapping("/{name}/{locationId}")
    public GroceryList createGroceryList (@RequestHeader("Authorization") String token, @PathVariable String name, @PathVariable String locationId){
        return groceryListService.createGroceryList(name, locationId, parseToken(token).getUserId());
    }

    @PostMapping("/{name}/{locationId}/{productId}/{count}")
    public GroceryListProduct addProductToGroceryList (@RequestHeader("Authorization") String token, @PathVariable String name, @PathVariable String locationId,
                                                       @PathVariable String productId, @PathVariable String count) throws IOException {
        HashMap<ProductFilterTerms,String> searchMap = new HashMap<>();
        searchMap.put(ProductFilterTerms.locationId, locationId);
        searchMap.put(ProductFilterTerms.productId, productId);
        return groceryListService.addProductToGroceryList(name, parseToken(token).getUserId(), Integer.parseInt(count),  searchMap);
    }

    @DeleteMapping("/{name}/{locationId}")
    public List<GroceryListProduct> deleteGroceryList (@RequestHeader("Authorization") String token, @PathVariable String name){
        return groceryListService.deleteGroceryList(name, parseToken(token).getUserId());
    }

    @DeleteMapping("/{name}/{locationId}/{productId}")
    public GroceryListProduct deleteProductFromGroceryList (@RequestHeader("Authorization") String token, @PathVariable String name, @PathVariable String locationId, @PathVariable String productId) throws IOException {
        HashMap<ProductFilterTerms,String> searchMap = new HashMap<>();
        searchMap.put(ProductFilterTerms.locationId, locationId);
        searchMap.put(ProductFilterTerms.productId, productId);
        return groceryListService.deleteProductFromGroceryList(name, parseToken(token).getUserId(), searchMap);
    }

}