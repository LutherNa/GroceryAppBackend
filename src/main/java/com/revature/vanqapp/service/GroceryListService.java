package com.revature.vanqapp.service;

import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.GroceryListProduct;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.repository.GroceryListProductRepository;
import com.revature.vanqapp.repository.GroceryListRepository;
import com.revature.vanqapp.util.AuthTokenFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class GroceryListService {

    @Autowired
    GroceryListRepository groceryListRepository;

    @Autowired
    GroceryListProductRepository groceryListProductRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    AuthTokenFactoryBean authTokenFactoryBean;
  
    /**
     * Constructor that passes an AuthToken pool
     * @param pool the pool is then passed to a Repository
     */
//    public GroceryListService(ObjectPool<AuthToken> pool) throws IOException {
//        this.productService = new ProductService(pool);
//    }

    public List<GroceryList> getAllGroceryList(User user){
        return groceryListRepository.findByOwner(user);
    }

    /**
     * Takes a name, location, and userId and then returns a new GroceryList that was persisted
     * @param name the name of the grocerylist to be persisted
     * @param locationId the location to be persisted
     * @param userId the userId used to link to the groceryList
     * @return returns the persisted Grocerylist
     */
    public GroceryList createGroceryList(String name, String locationId, Integer userId){
        return groceryListRepository.save(new GroceryList(name, locationId, userService.findUserById(userId)));
    }

    public List<GroceryListProduct> viewGroceryList(String name, String locationId, Integer userId){
        GroceryList groceryList = groceryListRepository.findByOwnerAndListName(userService.findUserById(userId), name);
        return groceryListProductRepository.findByGroceryList(groceryList);
    }

    public List<GroceryListProduct> deleteGroceryList(String name, String locationId, Integer userId){
        GroceryList groceryList = groceryListRepository.findByOwnerAndListName(userService.findUserById(userId), name);
        return groceryListProductRepository.deleteAllByGroceryList(groceryList);
    }

    public GroceryListProduct addProductToGroceryList(String name, Integer userId, HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        GroceryList groceryList = groceryListRepository.findByOwnerAndListName(userService.findUserById(userId), name);
        List<Product> product_list = productService.getProductsByIdAndLocation(searchMap);
        Product product = product_list.get(0);

        if (((product.getClass()) == Product.class) && (groceryList.getClass() == GroceryList.class)) {
            GroceryListProduct groceryListProduct = new GroceryListProduct();
            groceryListProduct.setProduct(product);
            groceryListProduct.setGroceryList(groceryList);

            groceryListProductRepository.save(groceryListProduct);
            return groceryListProduct;
        }
        return new GroceryListProduct();
    }

    public GroceryListProduct deleteProductFromGroceryList(String name, Integer userId, HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        GroceryList groceryList = groceryListRepository.findByOwnerAndListName(userService.findUserById(userId), name);
        List<Product> product_list = productService.getProductsByIdAndLocation(searchMap);
        Product product = product_list.get(0);

        if (((product.getClass()) == Product.class) && (groceryList.getClass() == GroceryList.class)) {
            GroceryListProduct groceryListProduct = groceryListProductRepository.findByGroceryListAndProduct(groceryList, product);
            if (groceryListProductRepository.deleteByGroceryListAndProduct(groceryList, product) == 1){
                return groceryListProduct;
            } else{
                return new GroceryListProduct();
            }
        }
        return new GroceryListProduct();
    }
}
