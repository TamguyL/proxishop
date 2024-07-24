package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String SubCategoryName;
    private Double id_category;

    public ProductSubCategory(Double id, String subCategoryName) {
        this.id = id;
        SubCategoryName = subCategoryName;
    }


    public ProductSubCategory() {};

    public ProductSubCategory(double id, String subCategoryName, double categoryId) {
        this.id = id;
        this.SubCategoryName = subCategoryName;
        this.id_category = categoryId;
    }
}
