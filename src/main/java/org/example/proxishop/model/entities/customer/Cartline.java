package org.example.proxishop.model.entities.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cartline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_product;
    private int productQuantity;
    private int id_shoppingCart;

}
