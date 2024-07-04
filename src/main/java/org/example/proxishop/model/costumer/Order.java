package org.example.proxishop.model.costumer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@Entity
@Table(name="order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private Double finalPrice;


    private String tag;

    public Order() {};

    public Order(Double id, Double finalPrice, String tag) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.tag = tag;
    }

    public Order(Double finalPrice, String tag) {
        this.finalPrice = finalPrice;

        this.tag = tag;
    }
}

