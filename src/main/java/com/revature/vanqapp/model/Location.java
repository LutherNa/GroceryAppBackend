package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String locationId;
    private StoreAddress address;
    private String phone;
    private String name;
}
