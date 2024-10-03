package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdCategories;
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
     * Gère la soumission des catégories et sous-catégories.
     *
     * @param action               L'action à effectuer (ajout de catégorie ou ajout de produit).
     * @param website_name              Le nom de la base de données.
     * @param categoryName         Le nom de la catégorie.
     * @param subcategoryName1     Le nom de la première sous-catégorie.
     * @param subcategoryName2     Le nom de la deuxième sous-catégorie.
     * @param subcategoryName3     Le nom de la troisième sous-catégorie.
     * @param subcategoryName4     Le nom de la quatrième sous-catégorie.
     * @param subcategoryName5     Le nom de la cinquième sous-catégorie.
     * @param model                Le modèle Spring MVC.
     * @param redirectAttributes   Les attributs de redirection.
     * @return Le nom de la vue à afficher après la soumission.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping
    public String handleSubmit(
            @RequestParam("action") String action,
            @RequestParam("website_name") String website_name,
            @RequestParam("categories1") String categoryName,
            @RequestParam("subcategories1") String subcategoryName1,
            @RequestParam("subcategories2") String subcategoryName2,
            @RequestParam("subcategories3") String subcategoryName3,
            @RequestParam("subcategories4") String subcategoryName4,
            @RequestParam("subcategories5") String subcategoryName5,
            Model model,
            RedirectAttributes redirectAttributes) throws SQLException {

        System.out.println("Database name: " + website_name); // Ajouter un log pour vérifier le nom de la base de données
        BdCategories db = new BdCategories();
        db.insertCategoryAndSubCategory(categoryName, subcategoryName1, subcategoryName2, subcategoryName3, subcategoryName4, subcategoryName5, website_name);
        model.addAttribute("website_name", website_name);
        if ("ajoutcateg".equals(action)) {
            return "categories";
        } else if ("addProduct".equals(action)) {
            redirectAttributes.addFlashAttribute("website_name", website_name);
            return "redirect:/products";
        }
        return "error";
    }

    /**
     * Affiche le catalogue des produits.
     *
     * @param website_name Le nom de la base de données.
     * @param model   Le modèle Spring MVC.
     */
    @GetMapping("/gestionCategories")
    public String showCatalogue(@ModelAttribute("website_name") String website_name, Model model) {
        System.out.println("Database name: " + website_name); // Ajouter un log pour vérifier le nom de la base de données
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