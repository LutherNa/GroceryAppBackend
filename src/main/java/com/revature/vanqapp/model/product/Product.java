package com.revature.vanqapp.model.product;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;

import com.revature.vanqapp.model.GroceryList;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

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
@Entity
@IdClass(ProductLocationId.class)
@Data
@Table(name = "Products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product{
    @Id
//    @Column(nullable = false)
    private String productId;

    @Id
//    @Column
    @JsonIgnore
    private String locationId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String description;

    @Column
    private String UPC;

    @OneToMany
//    @Transient
    @JsonProperty("aisleLocations")
    private List<AisleLocation> aisleLocations;

    @Transient
    @JsonProperty("items")
    private List<Item> items;

    @Column
    @JsonIgnore
    private BigDecimal regularPrice;

//    Products shouldn't need to know which lists they belong to,
//      lists should know their products.
//      This was also causing Hibernate to fail due to mismatched keys; look into.
//    @OneToMany(mappedBy = "groceryListId")
//    private Set<GroceryList> list = new LinkedHashSet<>();

    public BigDecimal getRegularPrice(){
        this.regularPrice = items.get(0).getPrice().getRegular();
        return regularPrice;
    }
}
