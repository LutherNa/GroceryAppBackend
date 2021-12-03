package com.revature.vanqapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.product.Product;
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
