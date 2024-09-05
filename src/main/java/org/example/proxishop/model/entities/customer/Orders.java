package org.example.proxishop.model.entities.customer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tags;
    private Double finalPrice;


    public Orders(int id, Double finalPrice, String tags) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.tags = tags;
    }

    public Orders(Double finalPrice, String tags) {
        this.finalPrice = finalPrice;
        this.tags = tags;
    }

    public Orders() {};
}

