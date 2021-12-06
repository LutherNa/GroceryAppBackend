package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import com.revature.vanqapp.model.product.Product;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "GroceryLists")
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groceryListId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private User owner;

    @Column
    private String locationId;

//    @OneToMany(mappedBy = "productId")
    @OneToMany
    @JsonManagedReference
    private Set<Product> list = new HashSet<>();

    public GroceryList(){
    }

    public GroceryList(String name, String locationId, User owner){
        this.name = name;
        this.locationId = locationId;
        this.owner = owner;
    }
}
