package com.revature.vanqapp.model;

import lombok.Data;

@Data
public class Location {
    private String locationId;
    private String addressLine1;
    private String city;
    private String state;
    private int zipCode;
    private String phone;
    private String name;
}
