package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;
import org.example.proxishop.service.ProxiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ProxiShopService proxiShopService;

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
     * @param subcategories        tableau dans lequel on insère le nom des sous-catégories
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
            @RequestParam("subcategories") String[] subcategories, // Accepter un tableau de sous-catégories
            Model model,
            RedirectAttributes redirectAttributes) throws SQLException {


        BdCategories db = new BdCategories();
        db.insertCategoryAndSubCategory(categoryName, subcategories, website_name);

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

    //-----DELETE---CATEGORY-----------------------------------------------------------------------------------------------
    @PostMapping("/deleteCategory")
    public String deletecategory(@RequestParam int id_Category, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        // Récupérer l'objet Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Récupérer le nom de la base de données à partir de l'objet Authentication
        String website_name = getDatabaseNameFromAuthentication(authentication);


        // Vérifie si le nom de la BDD n'est pas "null" ou vide
        if (website_name == null || website_name.isEmpty()) {
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        //Appelle la méthode deleteCategory de BdCategories et lui transmets les paramètres
        BdCategories db = new BdCategories();
        db.deleteCategory(website_name, id_Category);

        // Ajoute le nom de la BDD au modèle et aux attributs de redirection
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("error", "la catégorie a bien été supprimée");

        return "redirect:/shopkeeper/dashboard";
    }


    //-----DELETE---SUBCATEGORY-----------------------------------------------------------------------------------------------
    @PostMapping("/deleteSubCategory")
    public String deleteSubcategory(@RequestParam int id_subCategory, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        // Récupérer l'objet Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Récupérer le nom de la base de données à partir de l'objet Authentication
        String website_name = getDatabaseNameFromAuthentication(authentication);


        // Vérifie si le nom de la BDD n'est pas "null" ou vide
        if (website_name == null || website_name.isEmpty()) {
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        //Appelle la méthode deleteSubCategoryProducts et deleteSubCategory de BdCategories et lui transmets les paramètres
        BdCategories db = new BdCategories();
        db.deleteSubCategoryProducts(website_name,id_subCategory);
        db.deleteSubCategory(website_name, id_subCategory);

        // Ajoute le nom de la BDD au modèle et aux attributs de redirection
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("error", "la sous-catégorie a bien été supprimée");

        return "redirect:/shopkeeper/dashboard";
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