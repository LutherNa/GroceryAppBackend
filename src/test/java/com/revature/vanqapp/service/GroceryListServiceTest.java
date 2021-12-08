package com.revature.vanqapp.service;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.GroceryListRepository;
import com.revature.vanqapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.revature.vanqapp.NotAKrogerApp.tokenPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GroceryListServiceTest {

    @InjectMocks
    GroceryListRepository mockGroceryListRepository;
    @InjectMocks
    UserRepository mockUserRepository;
    @Mock
    ProductService mockProductService;
    @Mock
    User mockUser;
    @Mock
    GroceryListService groceryListService;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllGroceryList() {
        List<GroceryList> groceryLists = new ArrayList<>();
        Mockito.when(mockGroceryListRepository.findByOwner(mockUser)).thenReturn(groceryLists);
        groceryListService.getAllGroceryList(mockUser);
    }

    @Test
    void createGroceryList() {
    }

    @Test
    void addProductToGroceryList() {
    }
}