package com.revature.vanqapp.model.product;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductLocationId implements Serializable {
    private String productId;
    private String locationId;
}
