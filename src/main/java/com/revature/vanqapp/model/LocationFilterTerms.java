package com.revature.vanqapp.model;

import lombok.Data;

/**
 * Filter terms used to search through the Kroger Location API
 */
public enum LocationFilterTerms {
    zipCode, latLong, lat, lon, radiusInMiles, limit, chain, department, locationId;

    @Data
    public static class LocationFilterTermsDummy {
        private String zipCode, latLong, lat, lon, radiusInMiles, limit, chain, department, locationId;
    }
}
