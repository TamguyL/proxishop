package org.example.proxishop.model.costumer;

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


    public Orders(Double id, Double finalPrice, String tags) {
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

