package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.ProductFilterTerms;
import com.revature.vanqapp.model.Product;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    /**
     * The authtoken stored in product service, In future possibly move to controller layer and pass as a dependency to all service layers.
     */
    AuthToken authToken;

    /**
     * In initializing the product Service, the authToken needs to be called
     * @throws IOException TokenService.getToken can throw an IOException
     */
    public ProductService() throws IOException {
        authToken = TokenService.getToken();
    }

    /**
     * Takes a hashmap of (FilterTerms,String) and returns a list of mapped Products in a list, can be an empty list
     * @param searchMap Parameters for the search
     * @return a list of Products or an empty list
     * @throws IOException throws IOException because getAPISearchResult throws an IOException
     */
    public List<Product> getProducts(HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        return parseArrayNodeToProducts(getAPISearchResult(searchMap));
    }

    /**
     * Checks the API if exists for a productID
     * @param productId takes in the productID of an item
     * @return returns True if exists, false otherwise
     * @throws IOException throws IOException because getAPISearchResult throws an IOException
     */
    public boolean verifyProductById(String productId) throws IOException {
        return !parseArrayNodeToProducts(getAPISearchResult(new HashMap<ProductFilterTerms,String>(){{put(ProductFilterTerms.productId,productId);}})).isEmpty();
    }

    /**
     * Takes a hashmap and returns an ArrayNode of Products
     * @param searchMap the hashmap of products using (FilterTerm (enum), String (search term))
     * @return Returns an Arraynode of all matching Products in Json format
     * @throws IOException throws IOException if unable to call an ObjectMapper
     */
    private ArrayNode getAPISearchResult(HashMap<ProductFilterTerms,String> searchMap) throws IOException {
        String searchBuilder = searchMap.keySet().stream().map(term -> "filter." + term + "=" + searchMap.get(term) + "&")
                .collect(Collectors.joining("", "https://api.kroger.com/v1/products?", ""));
        searchBuilder = searchBuilder.substring(0,searchBuilder.length()-1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(searchBuilder)
//                .url("https://api.kroger.com/v1/products?filter.location=01400943&filter.term=&filter.fulfillment=ais") //?filter.brand={{BRAND}}&filter.term={{TERM}}&filter.locationId={{LOCATION_ID}}")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + authToken.getAccess_token())
                .build();
        Response response = client.newCall(request).execute();
        return (ArrayNode) new ObjectMapper().readTree(response.body().string()).path("data");
    }

    /**
     * Takes an ArrayNode of products and returns a list of products mapped to the Product class
     * @param arrayNode an ArrayNode of products in json format
     * @return returns a list of Product
     * @throws JsonProcessingException if unable to map the Json to Products or the Json is improperly formatted
     */
    private List<Product> parseArrayNodeToProducts(ArrayNode arrayNode) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        List<Product> products = new ArrayList<>();
        if (arrayNode.isArray()) {
            for (final JsonNode objNode : arrayNode) {
                Product product = mapper.readValue(objNode.toString(), Product.class);
                products.add(product);
            }
        }
        return products;
    }
}
/**
  * "data":[{"productId":"0085631200277",
 *          "upc":"0085631200277",
 *          "aisleLocations":[],
 *          "brand":"Fairlife",
 *          "categories":["Dairy","Natural \u0026 Organic"],
 *          "countryOrigin":"UNITED STATES",
 *          "description":"Fairlife 2% Reduced Fat Ultra Filtered Lactose Free Milk",
 *          "images":
 *              [{"perspective":"front",
 *              "featured":true,
 *              "sizes":[{"size":"small",
 *              "url":"https://www.kroger.com/product/images/small/front/0085631200277"},
 *              {"size":"thumbnail","url":"https://www.kroger.com/product/images/thumbnail/front/0085631200277"},
 *              {"size":"medium","url":"https://www.kroger.com/product/images/medium/front/0085631200277"},
 *              {"size":"large","url":"https://www.kroger.com/product/images/large/front/0085631200277"},
 *              {"size":"xlarge","url":"https://www.kroger.com/product/images/xlarge/front/0085631200277"}]}],
 *           "items":[{"itemId":"0085631200277",
 *           "favorite":false,
 *           "fulfillment":{"curbside":false,"delivery":false,"inStore":false,"shipToHome":false},
 *           "size":"52 fl oz"}],"itemInformation":{},
 *           "temperature":{"indicator":"Refrigerated","heatSensitive":false}}],
 *           "meta":{"pagination":{"start":0,"limit":0,"total":1}}}
  */