package com.example.userapi.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Coordinates {
    private double lat;
    private double lng;
}
