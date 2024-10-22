package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.service.ProxiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class ProductController {


    @Autowired
    private ProxiShopService proxiShopService;

    @GetMapping("/products/addProduct")
    public String showAddProductForm(@RequestParam(required = false, defaultValue = "0") int id_subCategory, Model model) {
        model.addAttribute("id_subCategory", id_subCategory);
        return "addProductForm"; // Nom de la vue Thymeleaf
    }

    @PostMapping("/products/addProducts")
    public String addProducts(@RequestParam int id_subCategory, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam int stock, @RequestParam String image,
                              Model model, RedirectAttributes redirectAttributes) throws SQLException {
        // Récupérer l'objet Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Récupérer le nom de la base de données à partir de l'objet Authentication
        String website_name = getDatabaseNameFromAuthentication(authentication);


        // Vérifier que le nom de la base de données n'est pas null ou vide
        if (website_name == null || website_name.isEmpty()) {

            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        // Insérer le nouveau produit
        BdProducts db = new BdProducts();
        db.insertNewProduct(id_subCategory, website_name, productName, description, price, stock, image);

        // Ajouter le nom de la base de données au modèle et aux attributs de redirection
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);

        return "redirect:/products";
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        // Vous pouvez ajouter ici la logique pour récupérer la liste des produits si nécessaire
        return "products"; // Nom de la vue Thymeleaf
    }

    private String getDatabaseNameFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Shopkeepers shopkeepers = proxiShopService.findByEmail(authentication.getName());
            if (shopkeepers != null) {
                return shopkeepers.getWebsiteName();
            }
        }
        return null;
    }
}