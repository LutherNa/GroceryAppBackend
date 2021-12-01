package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.FilterTerms;
import com.revature.vanqapp.model.Product;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    AuthToken authToken;

    public ProductService() throws IOException {
        authToken = TokenService.getToken();
    }

    public List<Product> getProducts(HashMap<FilterTerms,String> searchMap) throws IOException {
        return parseArrayNodeToProducts(getAPISearchResult(searchMap));
    }

    public boolean verifyProductById(String productId) throws IOException {
        return !parseArrayNodeToProducts(getAPISearchResult(new HashMap<FilterTerms,String>(){{put(FilterTerms.productId,productId);}})).isEmpty();
    }

    private ArrayNode getAPISearchResult(HashMap<FilterTerms,String> searchMap) throws IOException {
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
    private List<Product> parseArrayNodeToProducts(ArrayNode arrayNode) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("ProductDeserializer");
        module.addDeserializer(Product.class, new ProductDeserializer(Product.class));
        mapper.registerModule(module);
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