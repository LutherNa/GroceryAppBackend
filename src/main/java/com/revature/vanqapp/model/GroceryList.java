package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "GroceryLists")
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groceryListId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User owner;

    @Column
    private String locationId;

//    @OneToMany(mappedBy = "productId")
//    @OneToMany
//    @JsonManagedReference
//    private Set<Product> list = new HashSet<>();

    public GroceryList(){
    }

    public GroceryList(String name, String locationId, User owner){
        this.name = name;
        this.locationId = locationId;
        this.owner = owner;
    }
}
