package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String CategoryName;

}
