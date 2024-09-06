package org.example.proxishop.controller;

import org.example.proxishop.model.database.shopkeeper.BdCreation;
import org.example.proxishop.model.entities.customer.*;
import org.example.proxishop.model.entities.shopkeeper.*;
import org.example.proxishop.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shopkeeper")
@SessionAttributes("bddname")
public class ShopkeeperController {

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

    @Autowired
    private DataService dataService;

    /**
     * Affiche la page du commerçant.
     *
     */
    @GetMapping
    public String shopkeeper() {
        return "shopkeeper";
    }

    /**
     * Affiche la page de création d'une nouvelle base de données.
     *
     */
    @GetMapping("/newbdd")
    public String newbdd() {
        return "newbdd";
    }

    /**
     * Crée une nouvelle base de données et initialise les tables nécessaires.
     *
     * @param bddname         Le nom de la nouvelle base de données.
     * @param firm_name       Le nom de la firme.
     * @param siret           Le numéro SIRET.
     * @param firstName       Le prénom du commerçant.
     * @param lastName        Le nom de famille du commerçant.
     * @param email           L'adresse email du commerçant.
     * @param adress          L'adresse du commerçant.
     * @param profilePicture  L'URL de la photo de profil du commerçant.
     * @param option          Une option supplémentaire.
     * @param model           Le modèle Spring MVC.
     * -
     * Pour créé les table il faut bien remplir la List<Class<?>> classes avec les models exemple.class
     *
     * Ne pas oublier d'ajouter le PASSWORD dans DatabaseManager
     */
    @PostMapping("/newbdd")
    public String newbdd(@RequestParam String bddname, @RequestParam String firm_name, @RequestParam Double siret,
                         @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                         @RequestParam String adress, @RequestParam String profilePicture, @RequestParam String option,
                         Model model) {
        dataService.saveDataProxi(bddname, firm_name, siret, firstName, lastName, email, adress, option);
        BdCreation db = new BdCreation();
        List<Class<?>> classes = Arrays.asList(Cartline.class, Customer.class, Orders.class, ShoppingCart.class,
                Customize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class, ProductSubCategory.class);
        Shopkeeper shopkeeper = new Shopkeeper(siret, firstName, lastName, email, adress, profilePicture);
        db.createDatabaseAndTables(bddname, classes, shopkeeper);
        model.addAttribute("bddname", bddname);
        return "redirect:/categories/gestionCategories";
    }
}