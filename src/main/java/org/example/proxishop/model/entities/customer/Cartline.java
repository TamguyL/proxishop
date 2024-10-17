package org.example.proxishop.model.entities.customer;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.proxishop.model.entities.shopkeeper.Product;

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

    @ManyToOne
    private Product product;

    @ManyToOne
    private ShoppingCart shoppingCart;

    public Cartline(int idProduct, int productQuantity) {
        this.id_product = idProduct;
        this.productQuantity = productQuantity;
    }
}
