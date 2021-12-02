package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.Product;
import com.revature.vanqapp.model.ProductDetailsFilterTerms;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.HashMap;

public class GroceryListService {

    AuthToken authToken;

    public GroceryListService() throws IOException {
        authToken = TokenService.getToken();
    }

    public Product getProductInformation(HashMap<ProductDetailsFilterTerms,String> searchMap) throws IOException {
        return getAPISearchResult(searchMap);
    }

    public Product getAPISearchResult(HashMap<ProductDetailsFilterTerms,String> searchMap) throws IOException {
        String searchBuilder = "https://api.kroger.com/v1/products/" + searchMap.get(ProductDetailsFilterTerms.productId) + "?"
                + "filter.locationId=" + searchMap.get(ProductDetailsFilterTerms.locationId);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(searchBuilder)
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + authToken.getAccess_token())
                .build();

        Response response = client.newCall(request).execute();
        final ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = (ObjectNode) mapper.readTree(response.body().string()).path("data");
        return mapper.readValue(objectNode.toString(), Product.class);
    }
}

/**
 * "data":{"productId":"0088491201425",
 * "upc":"0088491201425",
 * "aisleLocations":[{"bayNumber":"3","description":"AISLE 7","number":"7","numberOfFacings":"1","side":"L","shelfNumber":"2","shelfPositionInBay":"2"}],
 * "brand":"Honey Bunches of Oats",
 * "categories":["Breakfast"],
 * "countryOrigin":"UNITED STATES",
 * "description":"Honey Bunches of Oats® with Almonds Cereal",
 * "images":
 *      [{"perspective":"front",
 *      "featured":true,
 *      "sizes":[{"size":"thumbnail",
 *      "url":"https://www.kroger.com/product/images/thumbnail/front/0088491201425"},
 *      {"size":"small","url":"https://www.kroger.com/product/images/small/front/0088491201425"},
 *      {"size":"medium","url":"https://www.kroger.com/product/images/medium/front/0088491201425"},
 *      {"size":"large","url":"https://www.kroger.com/product/images/large/front/0088491201425"},
 *      {"size":"xlarge","url":"https://www.kroger.com/product/images/xlarge/front/0088491201425"}]},
 *      {"perspective":"right","sizes":[{"size":"medium","url":"https://www.kroger.com/product/images/medium/right/0088491201425"},
 *      {"size":"small","url":"https://www.kroger.com/product/images/small/right/0088491201425"},
 *      {"size":"large","url":"https://www.kroger.com/product/images/large/right/0088491201425"},
 *      {"size":"thumbnail","url":"https://www.kroger.com/product/images/thumbnail/right/0088491201425"},
 *      {"size":"xlarge","url":"https://www.kroger.com/product/images/xlarge/right/0088491201425"}]},
 *      {"perspective":"back","sizes":[{"size":"medium","url":"https://www.kroger.com/product/images/medium/back/0088491201425"},
 *      {"size":"thumbnail","url":"https://www.kroger.com/product/images/thumbnail/back/0088491201425"},
 *      {"size":"small","url":"https://www.kroger.com/product/images/small/back/0088491201425"},
 *      {"size":"large","url":"https://www.kroger.com/product/images/large/back/0088491201425"},
 *      {"size":"xlarge","url":"https://www.kroger.com/product/images/xlarge/back/0088491201425"}]},
 *      {"perspective":"left","sizes":[{"size":"medium","url":"https://www.kroger.com/product/images/medium/left/0088491201425"},
 *      {"size":"thumbnail","url":"https://www.kroger.com/product/images/thumbnail/left/0088491201425"},
 *      {"size":"small","url":"https://www.kroger.com/product/images/small/left/0088491201425"},
 *      {"size":"large","url":"https://www.kroger.com/product/images/large/left/0088491201425"},
 *      {"size":"xlarge","url":"https://www.kroger.com/product/images/xlarge/left/0088491201425"}]}],
 * "items":[{"itemId":"0088491201425","favorite":false,"fulfillment":{"curbside":true,"delivery":true,"inStore":true,"shipToHome":false},"price":{"regular":3.19,"promo":0},"size":"14.5 oz","soldBy":"UNIT"}],
 * "itemInformation":{"depth":"2.22","height":"10.75","width":"7.56"},
 * "temperature":{"indicator":"Ambient","heatSensitive":false}}
 */