package org.example.proxishop.controller;

import org.example.proxishop.model.database.DatabaseManager;
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
@SessionAttributes("bddname")
public class ProductController {

    // Initialisation de l'attribut de session
    @ModelAttribute("bddname")
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
     * @param bddname Le nom de la base de données.
     * @param model   Le modèle Spring MVC.
     */
    @GetMapping
    public String creaProd(@ModelAttribute("bddname") String bddname, Model model) {
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            List<ProductCategory> categoryNamesList = databaseManager.getAllCategories(bddname);
            model.addAttribute("categoryNamesList", categoryNamesList);
            List<ProductSubCategory> subCategoryList = databaseManager.getAllSubCategories(bddname);
            model.addAttribute("subCategoryList", subCategoryList);
            List<Product> productList = databaseManager.getAllProducts(bddname);
            model.addAttribute("productList", productList);
            model.addAttribute("bddname", bddname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "products";
    }

    /**
     * Ajoute un nouveau produit à la base de données.
     *
     * @param subCategoryid L'ID de la sous-catégorie.
     * @param bddname       Le nom de la base de données.
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
    public String addProducts(@RequestParam double subCategoryid, @RequestParam String bddname, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam double stock, @RequestParam String image,
                              Model model, RedirectAttributes redirectAttributes) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.insertNewProduct(subCategoryid, bddname, productName, description, price, stock, image);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/products";
    }

    /**
     * Met à jour un produit existant dans la base de données.
     *
     * @param subCategoryid L'ID de la sous-catégorie.
     * @param bddname       Le nom de la base de données.
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
    public String updateProducts(@RequestParam double subCategoryid, @RequestParam String bddname, @RequestParam String productName,
                                 @RequestParam String description, @RequestParam double price, @RequestParam double stock, @RequestParam String image,
                                 @RequestParam int id_product, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.updateProduct(id_product, subCategoryid, bddname, productName, description, price, stock, image);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/products";
    }

    /**
     * Supprime un produit de la base de données.
     *
     * @param bddname     Le nom de la base de données.
     * @param productName Le nom du produit.
     * @param id_product  L'ID du produit à supprimer.
     * @param model       Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/deleteProducts")
    public String deleteProducts(@RequestParam String bddname, @RequestParam String productName, @RequestParam int id_product,
                                 Model model, RedirectAttributes redirectAttributes) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.deleteProduct(id_product, bddname, productName);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/products";
    }
}