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
    private int id;
    private String productName;
    private String description;
    private Double stock;
    private String image;
    private Double price;
    private int id_subCategory;


    public Product() {}

    public Product( String productName,String description, Double stock, String image, Double price, int id_subCategory) {

        this.productName = productName;
        this.id_subCategory = id_subCategory;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.price = price;
    }


    public Product(int id, String productName, String description, Double stock, String image, Double price, int id_subCategory) {
        this.id = id;
        this.productName = productName;
        this.id_subCategory = id_subCategory;
        this.stock = stock;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
