package com.revature.vanqapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="grocerylist_product")
public class GroceryListProduct {

    //private GroceryList groceryList;
    //private Product product;

    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "product_list_id")
    private long productListId;

}
