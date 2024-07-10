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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class Controllertest {

    @GetMapping("/proxishop")
    public String proxishop() {
        return "proxishop";
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
     * pour le moment il faut entrée le nom de la base de donner dans l'url
     * --
     * ! Amelioration : faire la reception du nom par un formulaire get (voir secu si OK) !
     * --
     * Pour créé les table il faut bien remplir la List<Class<?>> classes avec les models exemple.class
     * --
     * ! Ne pas oublier de transferer les info du shopkeeper sur son nouveau site !
     */
    @GetMapping("/newbdd/{bddname}")
    public String newbdd(@PathVariable String bddname) {
        DatabaseCreator db = new DatabaseCreator();
        List<Class<?>> classes = Arrays.asList(Cartline.class, Costumer.class, Orders.class, ShoppingCart.class, Costumize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class);
        db.createDatabaseAndTables(bddname, classes);
        return "newbdd";
    }
}
