package com.example.userapi.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String address;
    private String city;
    private String state;
    private String stateCode;
    private String postalCode;
    
    private String country;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "lat", column = @Column(name = "latitude")),
        @AttributeOverride(name = "lng", column = @Column(name = "longitude"))
    })
    private Coordinates coordinates;
}
