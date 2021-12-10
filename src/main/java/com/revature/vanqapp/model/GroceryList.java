package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "GroceryLists")
public class GroceryList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int groceryListId;

    @Column(nullable = false)
    private String listName;

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

    public GroceryList(String listName, String locationId, User owner){
        this.listName = listName;
        this.locationId = locationId;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroceryList that = (GroceryList) o;
        return groceryListId != 0 && Objects.equals(groceryListId, that.groceryListId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override

    public String toString() {
        return "{groceryListId:" + groceryListId +
                ",listName:" + listName +
                ",ownerUsername:" + owner.getUsername() +
                ",locationId:" + locationId + "}";
    }
}
