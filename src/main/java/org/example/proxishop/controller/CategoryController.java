package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/categories")
@SessionAttributes("website_name")
public class CategoryController {

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
     * Affiche la page de gestion des catégories.
     *
     */
    @GetMapping
    public String categories() {
        return "categories";
    }

    /**
     * Gère l'ajout d'une catégorie via une requête POST.
     *
     * @param website_name      Le nom de la base de données.
     * @param categoryName      Le nom de la catégorie.
     * @return Une réponse JSON indiquant le succès ou l'échec de l'opération.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/addCategory")
    @ResponseBody
    public String addCategory(
            @RequestParam("website_name") String website_name,
            @RequestParam("categoryName") String categoryName) throws SQLException {

        BdCategories db = new BdCategories();
        db.insertCategoryAndSubCategory(categoryName, new String[0], website_name);

        return "{\"status\": \"success\"}";
    }

    /**
     * Gère l'ajout de sous-catégories via une requête POST.
     *
     * @param website_name      Le nom de la base de données.
     * @param categoryId        L'ID de la catégorie.
     * @param subCategoryName   Le nom de la sous-catégorie.
     * @return Une réponse JSON indiquant le succès ou l'échec de l'opération.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/addSubCategory")
    @ResponseBody
    public String addSubCategory(
            @RequestParam("website_name") String website_name,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("subCategoryName") String subCategoryName) throws SQLException {

        // Ajouter des logs pour vérifier les paramètres reçus
        System.out.println("Received website_name: " + website_name);
        System.out.println("Received categoryId: " + categoryId);
        System.out.println("Received subCategoryName: " + subCategoryName);

        BdCategories db = new BdCategories();
        db.insertSubCategory(categoryId, subCategoryName, website_name);

        return "{\"status\": \"success\"}";
    }

    /**
     * Affiche le catalogue des produits.
     *
     * @param website_name Le nom de la base de données.
     * @param model   Le modèle Spring MVC.
     */
    @GetMapping("/gestionCategories")
    public String showCatalogue(@ModelAttribute("website_name") String website_name, Model model) {

        BdCategories db = new BdCategories();
        try {

            List<ProductCategory> categoryNamesList = db.getAllCategories(website_name);
            model.addAttribute("categoryNamesList", categoryNamesList);

            List<ProductSubCategory> subCategoryList = db.getAllSubCategories(website_name);
            model.addAttribute("subCategoryList", subCategoryList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "gestionCategories";
    }
}