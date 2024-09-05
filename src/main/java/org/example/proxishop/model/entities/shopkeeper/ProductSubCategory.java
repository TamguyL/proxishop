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
    private int id;
    private String SubCategoryName;
    private int id_category;

    public ProductSubCategory(int id, String subCategoryName) {
        this.id = id;
        SubCategoryName = subCategoryName;
    }

    public ProductSubCategory(String subCategoryName,int id_category) {
        this.id_category = id_category;
        this.SubCategoryName = subCategoryName;
    }


    public ProductSubCategory() {};

    public ProductSubCategory(int id, String subCategoryName, int categoryId) {
        this.id = id;
        this.SubCategoryName = subCategoryName;
        this.id_category = categoryId;
    }
}
