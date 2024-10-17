package org.example.proxishop.model.entities.customer;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.proxishop.model.entities.shopkeeper.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double totalPrice;
    private int id_customer;
    private List<Cartline> cartlines = new ArrayList<>();

    // Méthode pour ajouter un produit au panier
    public void addProductToCart(Product product, int quantity) {
        for (Cartline line : cartlines) {
            if (line.getProduct().getId() == product.getId()) {
                line.setProductQuantity(line.getProductQuantity() + quantity);
                recalculateTotalPrice();
                return;
            }
        }
        Cartline newLine = new Cartline();
        newLine.setProduct(product);
        newLine.setProductQuantity(quantity);
        cartlines.add(newLine);
        recalculateTotalPrice();
    }

    // Méthode pour recalculer le prix total
    public void recalculateTotalPrice() {
        totalPrice = 0.0;
        for (Cartline line : cartlines) {
            totalPrice += line.getProduct().getPrice() * line.getProductQuantity();
        }
    }
}
