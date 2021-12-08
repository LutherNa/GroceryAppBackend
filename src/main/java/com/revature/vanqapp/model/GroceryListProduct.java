package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.revature.vanqapp.model.product.Product;
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
    @JoinColumns({@JoinColumn(name = "productId"), @JoinColumn(name = "locationId")})
    private Product product;
    private int quantity = 1;

}
