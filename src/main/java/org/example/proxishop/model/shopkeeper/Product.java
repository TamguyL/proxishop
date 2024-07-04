package org.example.proxishop.model.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//@Entity
@Table(name="product")
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

    public Product(Double id, String productName, String description, Double stock, String image, Double price, Double category) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.price = price;
        this.id_category = category;
    }

    public Product(String productName, String description, Double stock, String image, Double price, Double category) {
        this.productName = productName;
        this.description = description;
        this.stock = stock;
        this.image = image;
        this.price = price;
        this.id_category = category;
    }

}
