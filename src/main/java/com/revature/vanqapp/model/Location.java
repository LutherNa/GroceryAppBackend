package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Table;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String locationId;
    private StoreAddress address;
    private String phone;
    private String name;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Table(name = "address")
    public static class StoreAddress {
        private String addressLine1;
        private String city;
        private String state;
        private String zipCode;
        private String county;
    }
}
