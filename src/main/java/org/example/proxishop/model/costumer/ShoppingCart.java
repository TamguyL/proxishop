package org.example.proxishop.model.costumer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@Entity
@Table(name="shoppingcart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private Double totalPrice;

    private Double id_costumer;



    public ShoppingCart(Double id, Double totalPrice, Double id_costumer) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.id_costumer = id_costumer;
    }
    public ShoppingCart(Double totalPrice, Double id_costumer) {
        this.totalPrice = totalPrice;
        this.id_costumer = id_costumer;
    }
    public ShoppingCart() {}
}
