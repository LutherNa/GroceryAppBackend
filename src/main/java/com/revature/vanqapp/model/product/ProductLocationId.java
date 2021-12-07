package com.revature.vanqapp.model.product;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ProductLocationId implements Serializable {
    private String productId;
    private String locationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductLocationId pid = (ProductLocationId) o;
        return productId.equals(pid.productId) &&
                locationId.equals(pid.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, locationId);
    }
}
