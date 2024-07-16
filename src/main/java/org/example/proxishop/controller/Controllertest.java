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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                         @RequestParam String adress, @RequestParam String profilePicture, @RequestParam String option) {
        dataService.saveDataProxi(bddname, firm_name, siret, firstName, lastName, email, adress, option);
        DatabaseManager db = new DatabaseManager();
        List<Class<?>> classes = Arrays.asList(Cartline.class, Customer.class, Orders.class, ShoppingCart.class,
                Customize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class);
        Shopkeeper shopkeeper = new Shopkeeper(siret, firstName, lastName, email, adress, profilePicture);
        db.createDatabaseAndTables(bddname, classes, shopkeeper);
        return "newbdd";
    }

    @GetMapping("/categories")
    public String categories(){return "categories";}
    @PostMapping("/categories")
    public String handleSubmit(@RequestParam Map<String, String> allParams) {
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

        // Affiche les catégories et produits pour la vérification
        System.out.println("Catégories:");
        for (Map.Entry<Double, String> category : categories.entrySet()) {
            System.out.println("ID: " + category.getKey() + ", Nom: " + category.getValue());
        }

        System.out.println("Produits:");
        for (Product product : products) {
            System.out.println("ID: " + product.getId() + ", Nom: " + product.getProductName() + ", Catégorie ID: " + product.getId_category());
        }

        // Retourne une vue ou redirige selon votre logique
        return "categories";
    }
    }




//    @PostMapping("/categories")
//    public List<ProductCategory> categories(@RequestParam Map<String, String> allParams) {
//        List<ProductCategory> categories = new ArrayList<>();
//        List<Product> products = new ArrayList<>();
//
//        // Parcourir les paramètres pour extraire les catégories et les produits
//        allParams.forEach((key, value) -> {
//            if (key.startsWith("categoryName_")) {
//                Double categoryId = Double.valueOf(key.substring(key.indexOf('_') + 1));
//                ProductCategory category = new ProductCategory(categoryId, value);
//                categories.add(category);
//            } else if (key.startsWith("productName_")) {
//                String[] parts = key.split("_");
//                Double productId = Double.valueOf(parts[1]);
//                Double categoryId = Double.valueOf(parts[2]);
//                Product product = new Product(productId, value, categoryId);
//                products.add(product);
//            }
//        });
//
//        // Associer les produits à leurs catégories
//        categories.forEach(category -> {
//            category.setProducts(new ArrayList<>());
//            products.forEach(product -> {
//                if (product.getId_category().equals(category.getId())) {
//                    category.getProducts().add(product);
//                }
//            });
//        });
//        return categories;
//    }
//}
