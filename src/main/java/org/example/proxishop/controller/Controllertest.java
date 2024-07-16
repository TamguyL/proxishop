package org.example.proxishop.controller;

import org.example.proxishop.model.database.DatabaseManager;
import org.example.proxishop.model.entities.shopkeeper.Customize;
import org.example.proxishop.model.entities.shopkeeper.Product;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;
import org.example.proxishop.model.entities.shopkeeper.SocialMedia;
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

    @GetMapping("/shopkeeper")
    public String shopkeeper() {
        return "shopkeeper";
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
                Customize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class);
        Shopkeeper shopkeeper = new Shopkeeper(siret, firstName, lastName, email, adress, profilePicture);
        db.createDatabaseAndTables(bddname, classes, shopkeeper);
        model.addAttribute("bddname", bddname);
        return "categories";
    }

    @PostMapping("/categories")
    public String handleSubmit(@RequestParam Map<String, String> allParams) throws SQLException {
        String bddname = allParams.get("bddname");
        Map<Double, String> categories = new HashMap<>();
        List<Product> products = new ArrayList<>();

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.startsWith("categories[")) {
                String[] parts = key.split("\\]");
                if (parts.length >= 2) {
                    String categoryPart = parts[0].replace("categories[", "");
                    double categoryId = Double.parseDouble(categoryPart);

                    if (key.contains(".name") && !key.contains(".products")) {
                        categories.put(categoryId, value);
                    } else if (key.contains(".products[")) {
                        String productPart = parts[1].replace(".products[", "").split("\\.")[0];
                        double productId = Double.parseDouble(productPart);

                        if (key.contains(".name")) {
                            products.add(new Product(productId, value, categoryId));
                        }
                    }
                }
            }
        }

        // Enregistre les catégories et produits dans la bdd du shopkeeper
        System.out.println("Catégories:");
        for (Map.Entry<Double, String> category : categories.entrySet()) {
            DatabaseManager db = new DatabaseManager();
            db.insertProductCategoryData(category.getKey(), category.getValue(), bddname);
        }

        System.out.println("Produits:");
        for (Product product : products) {
            DatabaseManager db = new DatabaseManager();
            db.insertProductData(product.getId(), product.getProductName(), product.getId_category(), bddname);
        }

        // Retourne une vue ou redirige selon votre logique
        return "index";
    }
    }
