package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.repository.GroceryListProductRepository;
import com.revature.vanqapp.repository.GroceryListRepository;
import com.revature.vanqapp.repository.UserRepository;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static com.revature.vanqapp.NotAKrogerApp.tokenPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GroceryListServiceTest {

    GroceryListService groceryListService;
    public static GenericObjectPool<AuthToken> tokenPool;
    @Mock
    GroceryListProductRepository mockGroceryListProductRepository;
    @Mock
    GroceryListRepository mockGroceryListRepository;
    @Mock
    User mockUser;
    @Mock
    UserService mockUserService;
    @Mock
    ProductService mockProductService;

    @BeforeEach
    public void setup() throws IOException {
        groceryListService = new GroceryListService(tokenPool);
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(
                groceryListService,
                "groceryListRepository",
                mockGroceryListRepository);
        ReflectionTestUtils.setField(
                groceryListService,
                "groceryListProductRepository",
                mockGroceryListProductRepository);
        ReflectionTestUtils.setField(
                groceryListService,
                "userService",
                mockUserService);
        ReflectionTestUtils.setField(
                groceryListService,
                "productService",
                mockProductService);
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