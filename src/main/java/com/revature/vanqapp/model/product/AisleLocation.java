package com.revature.vanqapp.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "aisleLocations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AisleLocation {
    @Id @Column
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
