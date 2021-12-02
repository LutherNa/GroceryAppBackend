package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
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

    @ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
    @JsonManagedReference
    private Set<GroceryList> list = new HashSet<>();


}
