package com.revature.vanqapp.model;

import lombok.Data;

import java.util.List;

@Data
public class GroceryListResponse {

    private GroceryList groceryList;
    private List<GroceryListProduct> listItems;

    public GroceryListResponse(GroceryList groceryList, List<GroceryListProduct> listItems) {
        this.groceryList = groceryList;
        this.listItems = listItems;
    }
}
