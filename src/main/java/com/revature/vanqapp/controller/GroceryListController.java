package com.revature.vanqapp.controller;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.service.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/grocerylist")
public class GroceryListController {

    @Autowired
    private GroceryListService groceryListService;

    @PostMapping("/{name}/{locationId}/{userId}")
    public GroceryList createGroceryList (@PathVariable String name, @PathVariable String locationId, @PathVariable String userId){
        return groceryListService.createGroceryList(name, locationId, Integer.parseInt(userId));
    }

    @PutMapping
    public List<GroceryList> getAllGroceryList(@RequestBody User user){
        return groceryListService.getAllGroceryList(user);
    }

}