package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String productName;
    private String description;
    private Double stock;
    private String image;
    private Double price;
    private Double id_category;


    public Product() {}

    public Product(Double id, String productName, Double category) {
        this.id = id;
        this.productName = productName;
        this.id_category = category;
        this.stock = (double) 0;
        this.description = null;
        this.image = null;
        this.price = (double) 0;
    }



}
