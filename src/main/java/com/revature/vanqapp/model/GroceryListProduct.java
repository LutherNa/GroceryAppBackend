package com.revature.vanqapp.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name="grocerylist_product")
public class GroceryListProduct {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "product_list_id")
    private long productListId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "groceryListId")
    private GroceryList groceryList;
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumns({@JoinColumn(name = "productId"), @JoinColumn(name = "locationId")})
    private Product product;
    private int quantity;

}
