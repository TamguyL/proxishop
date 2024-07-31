package org.example.proxishop.controller;

import org.example.proxishop.model.database.DatabaseManager;
import org.example.proxishop.model.entities.shopkeeper.*;
import org.example.proxishop.model.entities.customer.Cartline;
import org.example.proxishop.model.entities.customer.Customer;
import org.example.proxishop.model.entities.customer.Orders;
import org.example.proxishop.model.entities.customer.ShoppingCart;
import org.example.proxishop.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.FormSubmitEvent;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/")
public class Controllertest {

    @Autowired
    private DataService dataService;

    @GetMapping("/proxishop")
    public String proxishop() {
        return "proxishop";
    }

    @GetMapping("/template_choice1")
    public String template_choice1() {
        return "template_choice1";
    }


    @GetMapping("/template_choice2")
    public String template_choice2() {
        return "template_choice2";
    }
    @GetMapping("/shopkeeper")
    public String shopkeeper() {
        return "shopkeeper";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/mentions")
    public String mentions() {
        return "mentions";
    }
    @GetMapping("/cookies")
    public String cookies() {
        return "cookies";
    }


    /**
     * Création de la base de donner du nouveau site créé par le shopkeeper.
     * --
     * Pour créé les table il faut bien remplir la List<Class<?>> classes avec les models exemple.class
     * --
     * ! Ne pas oublier d'ajouter le PASSWORD dans DatabaseManager
     */
    @GetMapping("/newbdd")
    public String newbdd(){return "newbdd";}
    @PostMapping("/newbdd")
    public String newbdd(@RequestParam String bddname, @RequestParam String firm_name, @RequestParam Double siret,
                         @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                         @RequestParam String adress, @RequestParam String profilePicture, @RequestParam String option,
                         Model model) {
        dataService.saveDataProxi(bddname, firm_name, siret, firstName, lastName, email, adress, option);
        DatabaseManager db = new DatabaseManager();
        List<Class<?>> classes = Arrays.asList(Cartline.class, Customer.class, Orders.class, ShoppingCart.class,
                Customize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class, ProductSubCategory.class);
        Shopkeeper shopkeeper = new Shopkeeper(siret, firstName, lastName, email, adress, profilePicture);
        db.createDatabaseAndTables(bddname, classes, shopkeeper);
        model.addAttribute("bddname", bddname);
        return "categories";
    }

    // Première sauvegarde des categories et des produits sur la bdd du shopkeeper

    @GetMapping("/categories")
    public String categories(){return "categories";}
    @PostMapping("/categories")
    public String handleSubmit(
            @RequestParam("action") String action,
            @RequestParam("bddname") String bddname,
            @RequestParam("categories1") String categoryName,
            @RequestParam("subcategories1") String subcategoryName1,
            @RequestParam("subcategories2") String subcategoryName2,
            @RequestParam("subcategories3") String subcategoryName3,
            @RequestParam("subcategories4") String subcategoryName4,
            @RequestParam("subcategories5") String subcategoryName5,
            Model model) throws SQLException {

        DatabaseManager db = new DatabaseManager();
        db.insertCategoryAndSubCategory(categoryName, subcategoryName1, subcategoryName2, subcategoryName3, subcategoryName4, subcategoryName5, bddname);
        model.addAttribute("bddname", bddname);
        if ("ajoutcateg".equals(action)) {
            return "categories";
        } else if ("addProduct".equals(action)) {
            return "redirect:/products";
        }
        return "error";
    }

    // Première sauvegarde les reseaux sociaux sur la bdd du shopkeeper
    @PostMapping("/socialMedia")
    public String socialMedia(@RequestParam String bddname, @RequestParam String x, @RequestParam String instagram,
                              @RequestParam String facebook, @RequestParam String tiktok, @RequestParam String whatsapp,
                              Model model) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.insertSocialMedia(bddname, x, instagram, facebook, tiktok, whatsapp);
        model.addAttribute("bddname", bddname);
        return "index";
    }

    @GetMapping("/catalogue")
    public String showCatalogue(@RequestParam String bddname, Model model){
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            List<ProductCategory> categoryNamesList = databaseManager.getAllCategories(bddname);
            model.addAttribute("categoryNamesList", categoryNamesList);
            List<ProductSubCategory> subCategoryList = databaseManager.getAllSubCategories(bddname);
            model.addAttribute("subCategoryList", subCategoryList);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return "catalogue";
    }

    @GetMapping("/products")
    public String creaProd(@RequestParam String bddname, Model model){
        DatabaseManager databaseManager = new DatabaseManager();
        try{
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

    @PostMapping("/addProducts")
    public String addProducts(@RequestParam double subCategoryid, @RequestParam String bddname, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam double stock, @RequestParam String image,
                              Model model) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.insertNewProduct(subCategoryid, bddname, productName, description, price, stock, image);
        model.addAttribute("bddname", bddname);
        return "redirect:/products?bddname="+bddname;
    }

    @PostMapping("/updateProducts")
    public String updateProducts(@RequestParam double subCategoryid, @RequestParam String bddname, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam double stock, @RequestParam String image,
                              @RequestParam int id_product, Model model) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.updateProduct(id_product, subCategoryid, bddname, productName, description, price, stock, image);
        model.addAttribute("bddname", bddname);
        return "redirect:/products?bddname="+bddname;
    }

    @PostMapping("/deleteProducts")
    public String deleteProducts(@RequestParam String bddname, @RequestParam String productName, @RequestParam int id_product,
                                 Model model) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.deleteProduct(id_product, bddname, productName);
        model.addAttribute("bddname", bddname);
        return "redirect:/products?bddname="+bddname;
    }



}
