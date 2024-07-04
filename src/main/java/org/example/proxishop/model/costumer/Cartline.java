package org.example.proxishop.model.costumer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cartline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id_product;
    private Double productQuantity;
    private Double id_shoppingCart;


    public Cartline(Double product, Double productQuantity, Double shoppingCart) {
        this.id_product = product;
        this.productQuantity = productQuantity;
        this.id_shoppingCart = shoppingCart;
    }

    public Cartline() {};
}
