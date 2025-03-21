package com.example.userapi.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Crypto {
    private String coin;
    private String wallet;
    private String network;
}
