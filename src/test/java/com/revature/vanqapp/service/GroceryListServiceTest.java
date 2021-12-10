package com.revature.vanqapp.service;

import com.revature.vanqapp.model.*;
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
import java.util.HashMap;
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
    Product product;
    @Mock
    GroceryList mockGroceryList;
    @Mock
    UserService mockUserService;
    @Mock
    ProductService mockProductService;

    HashMap<ProductFilterTerms,String> searchMap;
    String name = "MyGroceryList";
    String locationId = "01400943";
    Integer userId = 0;
    Integer count = 1;
    User user;

    @BeforeEach
    public void setup() throws IOException {
        groceryListService = new GroceryListService();
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
        user = new User();
        user.setUserId(0);
        user.setUsername("testname");
        user.setPassword("testpassword");
        searchMap = new HashMap<>();
        searchMap.put(ProductFilterTerms.productId,"01203102301");
        searchMap.put(ProductFilterTerms.locationId,"91293189230");
    }

    @Test
    void getAllGroceryList() {
        List<GroceryList> groceryLists = new ArrayList<>();
        Mockito.when(mockGroceryListRepository.findByOwner(mockUser)).thenReturn(groceryLists);
        assert groceryListService.getAllGroceryList(mockUser) != null;
    }

    @Test
    void createGroceryList() {
        GroceryList groceryList = new GroceryList();
        groceryList.setGroceryListId(1);
        groceryList.setListName("Hi:");
        groceryList.setOwner(user);
        groceryList.setLocationId("91293189230");
        Mockito.when(mockGroceryListRepository.save(new GroceryList(name, locationId, mockUser))).thenReturn(groceryList);
        groceryListService.createGroceryList(name, locationId, userId);
    }

    @Test
    void viewGroceryList() {
        List<GroceryListProduct> groceryListProducts = new ArrayList<>();
        GroceryList groceryList = new GroceryList();
        Mockito.when(mockGroceryListRepository.findByOwnerAndListName(mockUserService.findUserById(userId), name)).thenReturn(groceryList);
        Mockito.when(mockGroceryListProductRepository.findByGroceryList(new GroceryList())).thenReturn(groceryListProducts);
        assert groceryListService.viewGroceryList(name, userId) != null;
    }

    @Test
    void deleteGroceryList() {
        List<GroceryListProduct> groceryListProducts = new ArrayList<>();
        GroceryList groceryList = new GroceryList();
        Mockito.when(mockGroceryListRepository.findByOwnerAndListName(mockUserService.findUserById(userId), name)).thenReturn(groceryList);
        Mockito.when(mockGroceryListProductRepository.deleteAllByGroceryList(new GroceryList())).thenReturn(groceryListProducts);
        assert groceryListService.viewGroceryList(name, userId) != null;
    }

    @Test
    void addProductToGroceryList() throws IOException {
        GroceryList groceryList = new GroceryList();
        List<Product> product_list = new ArrayList<>();
        GroceryListProduct groceryListProduct= new GroceryListProduct();
        Product product = new Product();
        product_list.add(product);
        Mockito.when(mockGroceryListRepository.findByOwnerAndListName(mockUserService.findUserById(userId), name)).thenReturn(groceryList);
        Mockito.when(mockProductService.getProductsByIdAndLocation(searchMap)).thenReturn(product_list);
        Mockito.when(mockGroceryListProductRepository.findByGroceryListAndProduct(mockGroceryList, product)).thenReturn(groceryListProduct);
        assert groceryListService.addProductToGroceryList(name, userId, count, searchMap) != null;
    }

    @Test
    void deleteProductFromGroceryList() throws IOException {
        GroceryList groceryList = new GroceryList();
        List<Product> product_list = new ArrayList<>();
        GroceryListProduct groceryListProduct= new GroceryListProduct();
        Product product = new Product();
        product_list.add(product);
        Mockito.when(mockGroceryListRepository.findByOwnerAndListName(mockUserService.findUserById(userId), name)).thenReturn(groceryList);
        Mockito.when(mockProductService.getProductsByIdAndLocation(searchMap)).thenReturn(product_list);
        Mockito.when(mockGroceryListProductRepository.findByGroceryListAndProduct(mockGroceryList, product)).thenReturn(groceryListProduct);
        assert groceryListService.deleteProductFromGroceryList(name, userId, searchMap) != null;
    }


}