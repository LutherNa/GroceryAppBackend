package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.Location;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.repository.KrogerApiRepository;
import com.revature.vanqapp.repository.ProductRepository;
import com.revature.vanqapp.util.AuthTokenFactoryBean;
import org.apache.commons.pool2.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    KrogerApiRepository krogerApiRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthTokenFactoryBean authTokenFactoryBean;

    /**
     * Constructor taking an AuthToken pool and
     * @param pool Send a pool of potentially available AuthTokens to be injected into the repository via
     * dependency injection
     */
//    public ProductService(ObjectPool<AuthToken> pool) {
//        krogerApiRepository = new KrogerApiRepository(pool);
//    }

    /**
     * Takes a hashmap of (FilterTerms,String) and returns a list of mapped Products in a list, can be an empty list
     * @param searchMap Parameters for the search
     * @return a list of Products or an empty list
     * @throws IOException throws IOException because getAPISearchResult throws an IOException
     */
    public List<Product> getProductsByIdAndLocation(HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        HashMap<ProductFilterTerms, String> filteredMap =
                (HashMap<ProductFilterTerms,String>) searchMap.entrySet().stream()
                .filter(map -> map.getKey().equals(ProductFilterTerms.productId)
                        || map.getKey().equals(ProductFilterTerms.locationId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (filteredMap.size() == 2) {
            return parseArrayNodeToProducts(getAPISearchResult(filteredMap), filteredMap);
        } else{ throw new InputMismatchException(); }
    }

    /**
     * Takes a Hashmap and an Arraylist then maps each filter term to its search parameter for the Kroger API
     * @param searchMap The hashmap of the filterterm and filter word
     * @param filterTerms Additional filter words
     * @return Returns a list of products that match the search
     * @throws IOException Return an IOException propagated from the API query
     */
    public List<Product> getProductsBySpecified(HashMap<ProductFilterTerms,String> searchMap, ArrayList<ProductFilterTerms>filterTerms) throws IOException {
        HashMap<ProductFilterTerms, String> filteredMap =
                (HashMap<ProductFilterTerms,String>) searchMap.entrySet().stream()
                        .filter(map -> filterTerms.contains(map.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (filteredMap.size() == filterTerms.size()) {
            return parseArrayNodeToProducts(getAPISearchResult(filteredMap), filteredMap);
        } else{
            throw new InputMismatchException();
        }
    }

    /**
     * Takes a single searchMap and returns a list of products
     * @param searchMap The productFilterTerms and matching values
     * @return Returns a completed list of Products
     * @throws IOException Returns an IOException propagated from the API query
     */
    public List<Product> getProducts(HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        return parseArrayNodeToProducts(getAPISearchResult(searchMap), searchMap);
    }

    /**
     * Checks the API if exists for a productID
     * @param productId takes in the productID of an item
     * @return returns True if exists, false otherwise
     * @throws IOException throws IOException because getAPISearchResult throws an IOException
     */
    public boolean verifyProductById(String productId) throws IOException {
        HashMap<ProductFilterTerms,String> searchMap= new HashMap<>();
        searchMap.put(ProductFilterTerms.productId,productId);
        return !parseArrayNodeToProducts(getAPISearchResult(searchMap),searchMap).isEmpty();
    }

    public Product deleteProduct(Product product){
        return productRepository.deleteByProductIdAndLocationId(product.getProductId(), product.getLocationId());
    }
    /**
     * Takes a hashmap and returns an ArrayNode of Products
     * @param searchMap the hashmap of products using (FilterTerm (enum), String (search term))
     * @return Returns an Arraynode of all matching Products in Json format
     */
    private JsonNode getAPISearchResult(HashMap<ProductFilterTerms,String> searchMap){
        String searchBuilder = searchMap.keySet().stream().map(term -> "filter." + term + "=" + searchMap.get(term) + "&")
                .collect(Collectors.joining("", "https://api.kroger.com/v1/products?", ""));
        searchBuilder = searchBuilder.substring(0,searchBuilder.length()-1);
        return krogerApiRepository.krogerAPIRequest(searchBuilder);
    }

    /**
     * Takes an ArrayNode of products and returns a list of products mapped to the Product class
     * @param jsonNode an ArrayNode of products in json format
     * @return returns a list of Product
     * @throws JsonProcessingException if unable to map the Json to Products or the Json is improperly formatted
     */
    private List<Product> parseArrayNodeToProducts(JsonNode jsonNode, HashMap<ProductFilterTerms,String> searchMap) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        List<Product> products = new ArrayList<>();
        if (jsonNode != null && jsonNode.isArray()) {
            for (final JsonNode objNode : jsonNode) {
                Product product = mapper.readValue(objNode.toString(), Product.class);
                if (searchMap.containsKey(ProductFilterTerms.locationId)) {product.setLocationId(searchMap.get(ProductFilterTerms.productId));}
                products.add(product);
            }
        }
        else if (jsonNode != null && !jsonNode.isArray()) {
            products.add(mapper.readValue(jsonNode.toString(), Product.class));
        }
        return products;
    }
}