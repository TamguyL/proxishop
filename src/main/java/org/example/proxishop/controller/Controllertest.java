package org.example.proxishop.controller;

import org.example.proxishop.DatabaseCreator;
import org.example.proxishop.model.shopkeeper.Costumize;
import org.example.proxishop.model.shopkeeper.Product;
import org.example.proxishop.model.shopkeeper.ProductCategory;
import org.example.proxishop.model.shopkeeper.Shopkeeper;
import org.example.proxishop.model.shopkeeper.SocialMedia;
import org.example.proxishop.model.costumer.Cartline;
import org.example.proxishop.model.costumer.Costumer;
import org.example.proxishop.model.costumer.Orders;
import org.example.proxishop.model.costumer.ShoppingCart;
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
     * ! Ne pas oublier de transferer les info du shopkeeper sur son nouveau site !
     */
    @GetMapping("/newbdd")
    public String newbdd(){return "newbdd";}
    @PostMapping("/newbdd")
    public String newbdd(@RequestParam String bddname, @RequestParam String firm_name, @RequestParam Double siret,
                         @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                         @RequestParam String adress, @RequestParam String profilePicture, @RequestParam String option) {
        dataService.saveDataProxi(bddname, firm_name, siret, firstName, lastName, email, adress, option);
        DatabaseCreator db = new DatabaseCreator();
        List<Class<?>> classes = Arrays.asList(Cartline.class, Costumer.class, Orders.class, ShoppingCart.class,
                Costumize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class);
        db.createDatabaseAndTables(bddname, classes);
        dataService.saveData(siret, firstName, lastName, email, adress, profilePicture);
        return "newbdd";
    }
}
