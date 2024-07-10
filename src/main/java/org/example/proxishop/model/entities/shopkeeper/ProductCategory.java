package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCategory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String CategoryName;
    private List<Product> products;


    public ProductCategory(Double id, String categoryName) {
        this.id = id;
        CategoryName = categoryName;
    }

    public ProductCategory(String categoryName, List<Product> products) {
        CategoryName = categoryName;
    }

    public ProductCategory() {};
}
