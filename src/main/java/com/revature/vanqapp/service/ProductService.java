package com.revature.vanqapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.revature.vanqapp.model.AuthToken;
import com.revature.vanqapp.model.Product;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    AuthToken authToken;

    public ProductService() throws IOException {
        authToken = TokenService.getToken();
    }

    public List<Product> getProducts() throws IOException {final ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.kroger.com/v1/products?filter.location=01400943&filter.term=cereal&filter.fulfillment=ais") //?filter.brand={{BRAND}}&filter.term={{TERM}}&filter.locationId={{LOCATION_ID}}")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + authToken.getAccess_token())
                .build();

        Response response = client.newCall(request).execute();
//        String body = response.body().string();
        SimpleModule module = new SimpleModule("ProductDeserializer");
        module.addDeserializer(Product.class, new ProductDeserializer(Product.class));
        mapper.registerModule(module);
        //System.out.println(response.body().string());
        //System.out.println(mapper.readValue(response.body().string(),Product.class));
        return mapper.readValue(response.body().string(), new TypeReference<List<Product>>(){});
//        System.out.println(body);
//        return mapper.readValue(body,Product[].class);
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