package com.revature.vanqapp.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    private BigDecimal regular;
    private BigDecimal promo;
}
