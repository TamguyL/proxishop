package org.example.proxishop.model.entities.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String tags;
    private Double finalPrice;
    private String orderNumber;
    private String state;


    public Orders(Double id, Double finalPrice, String tags, String orderNumber, String state) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.tags = tags;
        this.orderNumber = orderNumber;
        this.state = state;
    }
    public Orders(String tags, Double finalPrice, String orderNumber, String state) {
        this.tags = tags;
        this.finalPrice = finalPrice;
        this.orderNumber = orderNumber;
        this.state = state;
    }

    public Orders(Double finalPrice, String tags) {
        this.finalPrice = finalPrice;
        this.tags = tags;
    }

//    public Orders(String tag, double fprice, String onumber) {};
}

