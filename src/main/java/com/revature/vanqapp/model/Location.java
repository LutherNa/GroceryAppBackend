package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String locationId;
    private String addressLine1;
    private String city;
    private String state;
    private int zipCode;
    private String phone;
    private String name;
}
