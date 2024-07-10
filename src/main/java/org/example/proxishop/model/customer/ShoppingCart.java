package org.example.proxishop.model.customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private Double totalPrice;
    private Double id_customer;


    public ShoppingCart(Double id, Double totalPrice, Double id_customer) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.id_customer = id_customer;
    }
    public ShoppingCart(Double totalPrice, Double id_customer) {
        this.totalPrice = totalPrice;
        this.id_customer = id_customer;
    }
    public ShoppingCart() {}
}
