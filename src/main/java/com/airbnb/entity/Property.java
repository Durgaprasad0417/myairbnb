package com.airbnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "bed_rooms", nullable = false)
    private Integer bedRooms;

    @Column(name = "bath_rooms", nullable = false)
    private Integer bathRooms;

    @Column(name = "guests", nullable = false)
    private Integer guests;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightlyPrice;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}