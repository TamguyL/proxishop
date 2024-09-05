package org.example.proxishop.model.entities.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cartline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_product;
    private Double productQuantity;
    private int id_shoppingCart;


    public Cartline(int id_product, Double productQuantity, int id_shoppingCart) {
        this.id_product = id_product;
        this.productQuantity = productQuantity;
        this.id_shoppingCart = id_shoppingCart;
    }

    public Cartline() {};
}
