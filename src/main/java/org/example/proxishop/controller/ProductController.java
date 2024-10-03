package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.shopkeeper.Product;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/products")
@SessionAttributes("website_name")
public class ProductController {

    // Initialisation de l'attribut de session
    @ModelAttribute("website_name")
    public String setUpbddname() {
        return ""; // Initialisez avec une valeur par défaut ou null
    }

    // Méthode pour nettoyer la session
    @GetMapping("/clearSession")
    public String clearSession(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/index";
    }

    /**
     * Affiche la page de création de produits.
     *
     * @param website_name Le nom de la base de données.
     * @param model   Le modèle Spring MVC.
     */
    @GetMapping
    public String creaProd(@ModelAttribute("website_name") String website_name, Model model) {
        BdCategories db = new BdCategories();
        BdProducts dbp = new BdProducts();
        try {
            List<ProductCategory> categoryNamesList = db.getAllCategories(website_name);
            model.addAttribute("categoryNamesList", categoryNamesList);
            List<ProductSubCategory> subCategoryList = db.getAllSubCategories(website_name);
            model.addAttribute("subCategoryList", subCategoryList);
            List<Product> productList = dbp.getAllProducts(website_name);
            model.addAttribute("productList", productList);
            model.addAttribute("bddname", website_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "products";
    }

    /**
     * Ajoute un nouveau produit à la base de données.
     *
     * @param subCategoryid L'ID de la sous-catégorie.
     * @param website_name       Le nom de la base de données.
     * @param productName   Le nom du produit.
     * @param description   La description du produit.
     * @param price         Le prix du produit.
     * @param stock         Le stock disponible du produit.
     * @param image         L'URL de l'image du produit.
     * @param model         Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/addProducts")
    public String addProducts(@RequestParam double subCategoryid, @RequestParam String website_name, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam double stock, @RequestParam String image,
                              Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdProducts db = new BdProducts();
        db.insertNewProduct(subCategoryid, website_name, productName, description, price, stock, image);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        return "redirect:/products";
    }

    /**
     * Met à jour un produit existant dans la base de données.
     *
     * @param subCategoryid L'ID de la sous-catégorie.
     * @param website_name       Le nom de la base de données.
     * @param productName   Le nom du produit.
     * @param description   La description du produit.
     * @param price         Le prix du produit.
     * @param stock         Le stock disponible du produit.
     * @param image         L'URL de l'image du produit.
     * @param id_product    L'ID du produit à mettre à jour.
     * @param model         Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/updateProducts")
    public String updateProducts(@RequestParam double subCategoryid, @RequestParam String website_name, @RequestParam String productName,
                                 @RequestParam String description, @RequestParam double price, @RequestParam double stock, @RequestParam String image,
                                 @RequestParam int id_product, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdProducts db = new BdProducts();
        db.updateProduct(id_product, subCategoryid, website_name, productName, description, price, stock, image);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        return "redirect:/products";
    }

    /**
     * Supprime un produit de la base de données.
     *
     * @param website_name     Le nom de la base de données.
     * @param productName Le nom du produit.
     * @param id_product  L'ID du produit à supprimer.
     * @param model       Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/deleteProducts")
    public String deleteProducts(@RequestParam String website_name, @RequestParam String productName, @RequestParam int id_product,
                                 Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdProducts db = new BdProducts();
        db.deleteProduct(id_product, website_name, productName);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        return "redirect:/products";
    }
}