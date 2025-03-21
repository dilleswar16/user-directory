package com.example.userapi.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Bank {
    private String cardExpire;
    private String cardNumber;
    private String cardType;
    private String currency;
    private String iban;
}
