package com.example.userapi.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.userapi.config.LocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long id; // Unique user ID

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    private String maidenName;

    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Past(message = "Birthdate must be in the past")
    @JsonDeserialize(using = LocalDateDeserializer.class) // Custom deserializer
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Default format for serialization
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Helps with Spring MVC binding
    private LocalDate birthDate;
    
    private String image;
    private String bloodGroup;
    private double height;
    private double weight;
    private String eyeColor;

    @Embedded
    private Hair hair;

    private String ip;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "address", column = @Column(name = "user_address")),
        @AttributeOverride(name = "city", column = @Column(name = "user_city")),
        @AttributeOverride(name = "state", column = @Column(name = "user_state")),
        @AttributeOverride(name = "stateCode", column = @Column(name = "user_state_code")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "user_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "user_country")),
        @AttributeOverride(name = "coordinates.lat", column = @Column(name = "user_latitude")),
        @AttributeOverride(name = "coordinates.lng", column = @Column(name = "user_longitude"))
    })
    private Address address;

    private String macAddress;
    private String university;

    @Embedded
    private Bank bank;

    @Embedded
    private Company company;

    private String ein;

    @NotBlank(message = "SSN is required")
    @Column(unique = true, nullable = false)
    private String ssn;

    private String userAgent;

    @Embedded
    private Crypto crypto;

    @NotBlank(message = "Role is required")
    private String role;
}
