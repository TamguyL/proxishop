package org.example.proxishop.model.entities.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private int id_shoppingCart;


    public Customer(int id, String lastName, String firstName, String email, int id_shoppingCart) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.id_shoppingCart = id_shoppingCart;
    }

    public Customer(String lastName, String firstName, String email, int id_shoppingCart) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.id_shoppingCart = id_shoppingCart;

    }
    public Customer() {}
}
