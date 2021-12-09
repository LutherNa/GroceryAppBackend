package com.revature.vanqapp.model;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@IdClass(Product.ProductLocationId.class)
@Data
@Table(name = "Products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product{
    @Id
    private String productId;

    @Id
    @JsonIgnore
    private String locationId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String description;

    @Column
    private String UPC;

    @OneToMany(cascade = CascadeType.ALL)
//    @Transient
    @JsonProperty("aisleLocations")
    private List<AisleLocation> aisleLocations;

    @Transient
    @JsonProperty("items")
    private List<Item> items;

    @Column
    @JsonIgnore
    private BigDecimal regularPrice;

    public BigDecimal getRegularPrice(){
        this.regularPrice = items.get(0).getPrice().getRegular();
        return regularPrice;
    }

    public HashMap<ProductFilterTerms, String> toSearchableHashMap(Product product) {
        HashMap<ProductFilterTerms, String> productHashMap = new HashMap<>();
        if (!product.getProductId().isEmpty()) {
            productHashMap.put(ProductFilterTerms.productId,product.getProductId());
        }
        if (!product.getLocationId().isEmpty()) {
            productHashMap.put(ProductFilterTerms.locationId,product.getLocationId());
        }
        return (productHashMap.isEmpty()) ? null : productHashMap;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String itemId;
        private Price price;
        private String size;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Price {
            private BigDecimal regular;
            private BigDecimal promo;
        }
    }

    @Entity
    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    @Table(name = "aisleLocations")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AisleLocation {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int aisleLocationId;
        @Column
        private String bayNumber;
        @Column
        private String description;
        @Column
        private String AISLE;
        @Column
        private String number;
        @Column
        private String numberOfFacings;
        @Column
        private String side;
        @Column
        private String shelfNumber;
        @Column
        private String shelfPositionInBay;
    }

    @Data
    public static class ProductLocationId implements Serializable {
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
}
