package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"upc", "aisleLocation", "categories", "countryOrigin", "images", "items", "temperature"})
public class ProductData {
}
