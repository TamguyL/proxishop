package org.example.proxishop.controller;

import org.example.proxishop.model.database.costumer.BdOrder;
import org.example.proxishop.model.database.shopkeeper.BdCreation;
import org.example.proxishop.model.entities.customer.*;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.model.entities.shopkeeper.*;
import org.example.proxishop.service.ProxiShopService;
import org.example.proxishop.service.ShopkeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/shopkeeper")
@SessionAttributes("bddname")
public class ShopkeeperController {

    // Initialisation de l'attribut de session
    @ModelAttribute("bddname")
    public String setUpbddname() {

        return "";      // Initialisez avec une valeur par défaut ou null
    }


    // Méthode pour nettoyer la session
    @GetMapping("/clearSession")
    public String clearSession(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/index";
    }


    @Autowired
    private ProxiShopService proxiShopService;

    @Autowired
    ShopkeeperService shopkeeperService;


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
        Shopkeepers shopkeepers = new Shopkeepers();
        shopkeepers.setFirmName(firm_name);
        shopkeepers.setSiret(siret);
        shopkeepers.setFirstName(firstName);
        shopkeepers.setLastName(lastName);
        shopkeepers.setEmail(email);
        shopkeepers.setAdress(adress);
        shopkeepers.setWebSiteName(bddname);
        shopkeepers.setId_offer(Double.parseDouble(option));

        proxiShopService.saveShopkeeper(shopkeepers);

        Shopkeeper shopkeeper = new Shopkeeper(siret, firstName, lastName, email, adress, profilePicture);
        shopkeeperService.saveShopkeeper(shopkeeper);

        BdCreation db = new BdCreation();
        List<Class<?>> classes = Arrays.asList(Cartline.class, Customer.class, Orders.class, ShoppingCart.class,
                Customize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class, ProductSubCategory.class);
        db.createDatabaseAndTables(bddname, classes, shopkeeper);
        model.addAttribute("bddname", bddname);
        return "categories";
    }

    /**
     * Met à jour l'état d'une commande dans la base de données.
     *
     * @param id            L'ID de la commande
     * @param bddname       Le nom de la base de données.
     * @param model         Le modèle Spring MVC.
     * @return              La redirection vers la page des orders.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/updateOrder")
    public String updateOrder(@RequestParam double id, @RequestParam String bddname, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdOrder db = new BdOrder();

        db.updateOrderState(id,"prête","test");
        model.addAttribute("bddname", bddname);

        // Ajoute l'attribut temporaire pour la prochaine requête GET
        redirectAttributes.addFlashAttribute("bddname", bddname);

        // Redirige vers la page des orders
        return "redirect:/shopkeeper/orderlist";
    }


    @GetMapping("/orderlist")
    public String showOrderList(@ModelAttribute("bddname") String bddname,Model model) throws SQLException{

        // Utilisez bddname comme nécessaire
        model.addAttribute("bddname", bddname);

        // Récupérez la liste des orders et affichez-la
        BdOrder db = new BdOrder();
        List<Orders> orderList = db.getOrderlist("test");
        model.addAttribute("orderList", orderList);
        model.addAttribute("bddname", bddname);
        return "orderlist"; // Nom de la vue Thymeleaf ou JSP
    }

    /**
     * Affiche la page de connexion
     *
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


    /**
     * Gère la connexion des commerçants.
     *
     * @param email    L'adresse email du commerçant.
     * @param password Le mot de passe du commerçant.
     * @param model    Le modèle Spring MVC.
     * @return La redirection vers le tableau de bord si la connexion est réussie, sinon retourne la page de connexion avec un message d'erreur.
     */
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Shopkeeper shopkeeper = shopkeeperService.findByEmail(email);
        if (shopkeeper != null && shopkeeper.getPassword().equals(password)) {
            model.addAttribute("shopkeeper", shopkeeper);
            return "redirect:/shopkeeper/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    /**
     * Affiche le tableau de bord du commerçant.
     *
     * @param shopkeeper L'objet Shopkeeper représentant le commerçant connecté.
     * @param model       Le modèle Spring MVC.
     * @return La vue du tableau de bord.
     */
    @GetMapping("/dashboard")
    public String showDashboard(@ModelAttribute("shopkeeper") Shopkeeper shopkeeper, Model model) {
        model.addAttribute("shopkeeper", shopkeeper);
        return "dashboard";
    }
}