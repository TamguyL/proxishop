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
    private Double id_subCategory;


    public Product() {}

    public Product( String productName,String description, Double stock, String image, Double price, Double id_subCategory) {

        this.productName = productName;
        this.id_subCategory = id_subCategory;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.price = price;
    }



}
