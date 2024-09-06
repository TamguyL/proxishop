package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
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
@SessionAttributes("bddname")
public class CategoryController {

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
     * @param bddname              Le nom de la base de données.
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
            @RequestParam("bddname") String bddname,
            @RequestParam("categories1") String categoryName,
            @RequestParam("subcategories1") String subcategoryName1,
            @RequestParam("subcategories2") String subcategoryName2,
            @RequestParam("subcategories3") String subcategoryName3,
            @RequestParam("subcategories4") String subcategoryName4,
            @RequestParam("subcategories5") String subcategoryName5,
            Model model,
            RedirectAttributes redirectAttributes) throws SQLException {

        BdCategories db = new BdCategories();
        db.insertCategoryAndSubCategory(categoryName, subcategoryName1, subcategoryName2, subcategoryName3, subcategoryName4, subcategoryName5, bddname);
        model.addAttribute("bddname", bddname);
        if ("ajoutcateg".equals(action)) {
            return "categories";
        } else if ("addProduct".equals(action)) {
            redirectAttributes.addFlashAttribute("bddname", bddname);
            return "redirect:/products";
        }
        return "error";
    }

    /**
     * Affiche le catalogue des produits.
     *
     * @param bddname Le nom de la base de données.
     * @param model   Le modèle Spring MVC.
     */
    @GetMapping("/gestionCategories")
    public String showCatalogue(@ModelAttribute("bddname") String bddname, Model model) {
        BdCategories db = new BdCategories();
        try {
            List<ProductCategory> categoryNamesList = db.getAllCategories(bddname);
            model.addAttribute("categoryNamesList", categoryNamesList);
            List<ProductSubCategory> subCategoryList = db.getAllSubCategories(bddname);
            model.addAttribute("subCategoryList", subCategoryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "gestionCategories";
    }

    /**
     * Gère la soumission des catégories et sous-catégories.
     *
     * @param bddname              Le nom de la base de données.
     * @param categoryName         Le nom de la catégorie.
     * @param model                Le modèle Spring MVC.
     * @return Le nom de la vue à afficher après la soumission.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/addCategory")
    public String addCategory(
            @RequestParam("bddname") String bddname,
            @RequestParam("categories") String categoryName,
            Model model, RedirectAttributes redirectAttributes) throws SQLException {

        BdCategories db = new BdCategories();
        db.insertCategory(categoryName, bddname);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }

    /**
     * Met à jour la categories existante dans la base de données.
     *
     * @param bddname       Le nom de la base de données.
     * @param categoryName   Le nom de le category.
     * @param id_category    L'ID de la categorie à mettre à jour.
     * @param model         Le modèle Spring MVC.
     * @return La redirection vers la page des categories.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/updateCategory")
    public String updateCategory(@RequestParam String bddname, @RequestParam String categoryName,
                                 @RequestParam int id_category, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdCategories db = new BdCategories();
        db.updateCategory(id_category, bddname, categoryName);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }

    /**
     * Supprime un produit de la base de données.
     *
     * @param bddname     Le nom de la base de données.
     * @param id_category  L'ID de la categorie à supprimer.
     * @param model       Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam String bddname, @RequestParam int id_category,
                                 Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdCategories db = new BdCategories();
        db.deleteCategory(id_category, bddname);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }

    /**
     * Gère la soumission des catégories et sous-catégories.
     *
     * @param bddname              Le nom de la base de données.
     * @param subCategoryName         Le nom de la catégorie.
     * @param id_category  L'ID de la categorie à supprimer.
     * @param model                Le modèle Spring MVC.
     * @return Le nom de la vue à afficher après la soumission.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/addSubCategory")
    public String addSubCategory(
            @RequestParam("bddname") String bddname,
            @RequestParam("subCategoryName") String subCategoryName,
            @RequestParam("id_category") int id_category,
            Model model, RedirectAttributes redirectAttributes) throws SQLException {

        BdCategories db = new BdCategories();
        db.insertSubCategory(id_category, subCategoryName, bddname);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }

    /**
     * Met à jour la categories existante dans la base de données.
     *
     * @param bddname       Le nom de la base de données.
     * @param SubCategoryName   Le nom de le sous category.
     * @param id_subCategory    L'ID de la sous categorie à mettre à jour.
     * @param model         Le modèle Spring MVC.
     * @return La redirection vers la page des categories.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/updateSubCategory")
    public String updateSubCategory(@RequestParam String bddname, @RequestParam String SubCategoryName,
                                 @RequestParam int id_subCategory, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdCategories db = new BdCategories();
        db.updateSubCategory(id_subCategory, bddname, SubCategoryName);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }

    /**
     * Supprime un produit de la base de données.
     *
     * @param bddname     Le nom de la base de données.
     * @param id_subCategory  L'ID de la sous categorie à supprimer.
     * @param model       Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/deleteSubCategory")
    public String deleteSubCategory(@RequestParam String bddname, @RequestParam int id_subCategory,
                                 Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdCategories db = new BdCategories();
        db.deleteSubCategory(id_subCategory, bddname);
        model.addAttribute("bddname", bddname);
        redirectAttributes.addFlashAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }

}
