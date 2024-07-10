package org.example.proxishop.model.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String lastName;
    private String firstName;
    private String email;
    private Double id_shoppingCart;


    public Customer(Double id, String lastName, String firstName, String email, Double id_shoppingCart) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.id_shoppingCart = id_shoppingCart;
    }

    public Customer(String lastName, String firstName, String email, Double id_shoppingCart) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.id_shoppingCart = id_shoppingCart;

    }
    public Customer() {}
}
