package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.GroceryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Service
public class GroceryListService {

    AuthToken authToken;

    @Autowired
    GroceryListRepository groceryListRepository;

    @Autowired
    UserService userService;

    public GroceryListService() throws IOException {
        authToken = TokenService.getToken();
    }

    public List<GroceryList> getAllGroceryList(User user){
        return groceryListRepository.findByOwner(user);
    }

    public GroceryList createGroceryList(String name, String locationId, Integer userId){
        GroceryList groceryList = new GroceryList(name, locationId, userService.findUserById(userId));
        return groceryListRepository.save(groceryList);
    }
}
