package org.example.proxishop.controller;

import jakarta.servlet.http.HttpSession;
import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.customer.Cartline;
import org.example.proxishop.model.entities.shopkeeper.Product;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/site")
public class ClientController{

//    /**
//     * Affiche la page d'accueil du Client qui ne connait pas l'adresse du Shopkeeper
//     *
//     */
//    @GetMapping
//    public String website (){
//        return "website";
//    }



     /**
     * Affiche la page d'accueil du Client ShopKeeper
     *
     */
    @GetMapping("/{website_name}")
    public String clientwebsite (@PathVariable String website_name, HttpSession session, Model model){
        BdCategories db = new BdCategories();
        BdProducts dbp = new BdProducts();
        try {
            List<ProductCategory> categoryList = db.getAllCategories(website_name);
            model.addAttribute("categoryList", categoryList);
            List<ProductSubCategory> subCategoryList = db.getAllSubCategories(website_name);
            model.addAttribute("subCategoryList", subCategoryList);
            List<Product> productList = dbp.getAllProducts(website_name);
            model.addAttribute("productList", productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.setAttribute("website_name", website_name);
        model.addAttribute("website_name", website_name);
        return "website";
    }

    /**
     * Ajoute un produit au panier de l'utilisateur.
     */
    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable int productId, @RequestParam int quantity, HttpSession session, RedirectAttributes redirectAttributes) throws SQLException {
        // Récupérer le panier de la session ou créer un nouveau s'il n'existe pas
        List<Cartline> cart = (List<Cartline>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Vérifier si l'article est déjà dans le panier
        boolean found = false;
        for (Cartline item : cart) {
            if (item.getId_product() == productId) {
                // Si le produit est déjà dans le panier, mettre à jour la quantité
                item.setProductQuantity(item.getProductQuantity() + quantity);
                found = true;
                break;
            }
        }

        // Si le produit n'est pas encore dans le panier, l'ajouter
        if (!found) {
            cart.add(new Cartline(productId, quantity));
        }

        // Sauvegarder le panier dans la session
        session.setAttribute("cart", cart);

        redirectAttributes.addFlashAttribute("message", "Vous avez ajoutée " + quantity + " produits");

        // Rediriger vers la page du panier ou une autre page
        return "redirect:/site/"+session.getAttribute("website_name");
    }

    /**
     * Affiche le panier d'achat de l'utilisateur.
     */
    @GetMapping("/panier")
    public String viewCart(HttpSession session, Model model) {

        // Récupérer le panier depuis la session
        List<Cartline> cart = (List<Cartline>) session.getAttribute("cart");
        String websiteName = (String) session.getAttribute("website_name");

        if (cart == null || cart.isEmpty()) {
            model.addAttribute("message", "Votre panier est vide.");
            return "cart";
        }

        // Récupérer les détails des produits depuis la base de données
        BdProducts bdProducts = new BdProducts();
        Map<Product, Integer> productWithQuantities = new HashMap<>();  // Pour stocker produit et quantité
        double totalPrice = 0.0;

        try {
            for (Cartline cartline : cart) {
                Product product = bdProducts.getProductById(websiteName, cartline.getId_product());
                if (product != null) {
                    // Ajouter le produit avec la quantité dans la map
                    int quantity = cartline.getProductQuantity();
                    productWithQuantities.put(product, cartline.getProductQuantity());
                    totalPrice += product.getPrice() * quantity;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception
        }

        // Ajouter les produits détaillés au modèle
        model.addAttribute("productWithQuantities", productWithQuantities);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/panier/supprimer/{id_product}")
    public String supprimerProduitDuPanier(@PathVariable("id_product") int id_product, HttpSession session, Model model) {
        // Récupérer le panier depuis la session
        List<Cartline> cart = (List<Cartline>) session.getAttribute("cart");

        if (cart != null) {
            // Rechercher et supprimer la ligne du produit dans le panier
            cart.removeIf(cartline -> cartline.getId_product() == id_product);

            // Mettre à jour le panier dans la session
            session.setAttribute("cart", cart);
        }

        // Rediriger vers la page du panier après suppression
        return "redirect:/site/panier";
    }
}
