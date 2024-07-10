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

import java.util.Arrays;
import java.util.List;

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
        List shopkeeper = Arrays.asList(siret, firstName, lastName, email, adress, profilePicture);
        db.createDatabaseAndTables(bddname, classes, shopkeeper);
        return "newbdd";
    }


}
