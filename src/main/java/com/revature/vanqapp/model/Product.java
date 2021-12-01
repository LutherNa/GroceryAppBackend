package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import lombok.Data;
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
 *              "favorite":false,
 *              "fulfillment":{"curbside":false,"delivery":false,"inStore":false,"shipToHome":false},
 *              "size":"52 fl oz"}],
 *           "itemInformation":{},
 *           "temperature":{"indicator":"Refrigerated","heatSensitive":false}}],
 *  "meta":{"pagination":{"start":0,"limit":0,"total":1}}}
 */
@Data
@JsonIgnoreProperties({"meta"})
public class Product {
    private int productId;
    private String brand;
    private String description;

}
