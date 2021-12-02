package com.revature.vanqapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="grocerylist_product")
public class GroceryListProduct {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "product_list_id")
    private long productListId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "groceryListId")
    private GroceryList groceryList;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId")
    private Product product;

    private String aisle;
    private double price;

}
