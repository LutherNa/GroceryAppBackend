package com.revature.vanqapp.model;

import lombok.Data;

/**
 * Filter terms used to search through the Kroger API
 */
public enum ProductFilterTerms {
    term,locationId,productId,brand,fulfillment,start,limit;

    @Data
    public static class ProductFilterTermsDummy {
        private String term,locationId,productId,brand,fulfillment,start,limit;
    }
}
