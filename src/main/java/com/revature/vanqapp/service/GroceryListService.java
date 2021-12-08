package com.revature.vanqapp.service;

import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.GroceryList;
import com.revature.vanqapp.model.GroceryListProduct;
import com.revature.vanqapp.model.User;
import com.revature.vanqapp.model.product.AisleLocation;
import com.revature.vanqapp.model.product.Product;
import com.revature.vanqapp.model.product.ProductFilterTerms;
import com.revature.vanqapp.repository.GroceryListProductRepository;
import com.revature.vanqapp.repository.GroceryListRepository;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    public GroceryListService(ObjectPool<AuthToken> pool) throws IOException {
        this.productService = new ProductService(pool);
        this.userService = new UserService();
    }

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

    public GroceryListProduct addProductToGroceryList(String name, Integer userId, HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        GroceryList groceryList = groceryListRepository.findByOwnerAndName(userService.findUserById(userId), name);
        List<Product> product_list = productService.getProductsByIdAndLocation(searchMap);
        Product product = product_list.get(0);

        if (((product.getClass()) == Product.class) && (groceryList.getClass() == GroceryList.class)) {
            GroceryListProduct groceryListProduct = new GroceryListProduct();
            groceryListProduct.setProduct(product);
            groceryListProduct.setGroceryList(groceryList);

            List<AisleLocation> aisleLocations = product.getAisleLocations();
            AisleLocation aisleLocation = aisleLocations.get(0);
            String aisleNumber = aisleLocation.getNumber();

            groceryListProduct.setAisle(aisleNumber);
            groceryListProduct.setPrice(NumberFormat.getCurrencyInstance(Locale.US).format(product.getRegularPrice()));

            groceryListProductRepository.save(groceryListProduct);
            //System.out.println(groceryListProduct);
            return groceryListProduct;
        }
        return new GroceryListProduct();
    }
}
