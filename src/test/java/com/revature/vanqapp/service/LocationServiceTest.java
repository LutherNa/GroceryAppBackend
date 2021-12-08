package com.revature.vanqapp.service;

import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.GroceryListRepository;
import com.revature.vanqapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.revature.vanqapp.NotAKrogerApp.tokenPool;
import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    GroceryListRepository mockGroceryListRepository;
    UserRepository mockUserRepository;
    ProductService mockProductService;
    User mockUser;
    GroceryListService groceryListService;

    @BeforeEach
    public void setup() throws IOException {
        mockGroceryListRepository = Mockito.mock(GroceryListRepository.class);
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockProductService = Mockito.mock(ProductService.class);
        mockUser = new User();
        mockUser.setUserId(0);
        groceryListService = new GroceryListService(tokenPool);
    }

    @Test
    void getLocations() {
    }
}