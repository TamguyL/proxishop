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


    public Orders(Double id, Double finalPrice, String tags, String orderNumber) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.tags = tags;
        this.orderNumber = orderNumber;
    }
    public Orders(String tags, Double finalPrice, String orderNumber) {
        this.tags = tags;
        this.finalPrice = finalPrice;
        this.orderNumber = orderNumber;
    }

    public Orders(Double finalPrice, String tags) {
        this.finalPrice = finalPrice;
        this.tags = tags;
    }

//    public Orders(String tag, double fprice, String onumber) {};
}

