package org.example.proxishop.controller;
import jakarta.servlet.http.HttpSession;
import org.example.proxishop.Security.SiretValidator;
import org.example.proxishop.model.database.costumer.BdOrder;
import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.database.shopkeeper.BdCreation;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.customer.*;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.model.entities.shopkeeper.*;
import org.example.proxishop.service.ProxiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        return ""; // Initialisez avec une valeur par défaut ou null
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
    private PasswordEncoder passwordEncoder;


    /**
     * Affiche la page du commerçant.
     */
    @GetMapping
    public String shopkeeper() {
        return "shopkeeper";
    }


    /**
     * Met à jour l'état d'une commande dans la base de données.
     *
     * @param id            L'ID de la commande
     * @param website_name       Le nom de la base de données.
     * @param model         Le modèle Spring MVC.
     * @return              La redirection vers la page des orders.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/updateOrder")
    public String updateOrder(@RequestParam int id, @RequestParam String website_name, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdOrder db = new BdOrder();

        db.updateOrderState(id,"prête","test");
        model.addAttribute("website_name", website_name);

        // Ajoute l'attribut temporaire pour la prochaine requête GET
        redirectAttributes.addFlashAttribute("website_name", website_name);

        // Redirige vers la page des orders
        return "redirect:/shopkeeper/orderlist";
    }

    @GetMapping("/orderlist")
    public String showOrderList(@ModelAttribute("website_name") String website_name,Model model) throws SQLException{

        // Utilisez bddname comme nécessaire
        model.addAttribute("website_name", website_name);

        // Récupérez la liste des orders et affichez-la
        BdOrder db = new BdOrder();
        List<Orders> orderList = db.getOrderlist("test");
        model.addAttribute("orderList", orderList);
        model.addAttribute("website_name", website_name);
        return "orderlist"; // Nom de la vue Thymeleaf ou JSP
    }

    /**
     * Affiche la de création de compte
     *
     */
    @GetMapping("/accountCreation")
    public String showAccountCreationForm() {
        return "accountCreation";
    }

    @PostMapping("/accountCreation")
    public String newAccount(
                         @RequestParam String firstName, @RequestParam String lastName, @RequestParam String firm_name, @RequestParam String adress, @RequestParam String siret, @RequestParam String email,
                         @RequestParam String password, @RequestParam("profilePicture") MultipartFile file,
                         HttpSession session, Model model) throws IOException {

        // Vérifier si le SIRET est valide
        if (!SiretValidator.isValidSiret(siret)) {
            model.addAttribute("error", "Numéro siret non valide !");
            return "accountCreation";
        }
        // Vérifier si EMAIL n'est pas déjà enregistré
        if (proxiShopService.existsByEmail(email)) {
            model.addAttribute("error", "Utilisateur est déjà enregistré !");
            return "accountCreation";
        }

        // Validation du fichier image
        try {
            // Verifier si file n'est pas vide
            if (file.isEmpty()) {
                model.addAttribute("error", "L'image de profil est vide");
                return "accountCreation";
            }
            // Vérifier le format de image
            String contentType = file.getContentType();
            if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
                model.addAttribute("error", "Le format de l'image doit être PNG, JPG ou GIF");
                return "accountCreation";
            }
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                model.addAttribute("error", "Le fichier n'est pas une image valide");
                return "accountCreation";
            }
            // Vérifier taille de image
            if (image.getWidth() > 500 || image.getHeight() > 500) {
                model.addAttribute("error", "L'image ne doit pas dépasser 500x500 pixels");
                return "accountCreation";
            }
            // Sauvegarder image
            String destinationPath = System.getProperty("user.dir") + "/src/main/resources/static/uploads/profiles/" + file.getOriginalFilename();
            File dest = new File(destinationPath);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageUrl = "/uploads/profiles/" + file.getOriginalFilename();
        String encryptedPassword = passwordEncoder.encode(password);
        Shopkeepers shopkeepers = new Shopkeepers();
        shopkeepers.setFirstName(firstName);
        shopkeepers.setLastName(lastName);
        shopkeepers.setFirmName(firm_name);
        shopkeepers.setAdress(adress);
        shopkeepers.setSiret(siret);
        shopkeepers.setEmail(email);
        shopkeepers.setPassword(encryptedPassword);
        shopkeepers.setProfilePicture(imageUrl);

        proxiShopService.saveShopkeeper(shopkeepers);
        // Convertir Shopkeepers en Shopkeeper
        Shopkeeper shopkeeper = new Shopkeeper(
                shopkeepers.getId(),
                shopkeepers.getSiret(),
                shopkeepers.getFirstName(),
                shopkeepers.getLastName(),
                shopkeepers.getPassword(),
                shopkeepers.getEmail(),
                shopkeepers.getAdress(),
                shopkeepers.getProfilePicture()
        );


        session.setAttribute("shopkeeper", shopkeeper);

        return "redirect:/shopkeeper/newbdd";
    }


    @GetMapping("/newbdd")
    public String newbdd() {
        return "newbdd";
    }

        @PostMapping("/newbdd")
        public String createShopkeeperDB(HttpSession session,@RequestParam String website_name,@RequestParam int id_offer, Model model) {

            if (proxiShopService.existsByWebsiteName(website_name)) {
                model.addAttribute("error", "Nom du site déjà utilisé !");
                return "newbdd";
            }

            BdCreation db = new BdCreation();
            List<Class<?>> classes = Arrays.asList(Cartline.class, Customer.class, Orders.class, ShoppingCart.class,
                    Customize.class, Product.class, ProductCategory.class, Shopkeeper.class, SocialMedia.class, ProductSubCategory.class);

            Shopkeeper shopkeeper = (Shopkeeper) session.getAttribute("shopkeeper");
            proxiShopService.updateShopkeeper(shopkeeper.getId(), website_name, id_offer);
            db.createDatabaseAndTables(website_name, classes, shopkeeper);

            model.addAttribute("website_name", website_name);
            model.addAttribute("id_offer", id_offer);
            return "login";
        }

    /**
     * Affiche la page de connexion
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

//    /**
//     * Gère la connexion des commerçants.
//     *
//     * @param username    L'adresse email du commerçant.
//     * @param password Le mot de passe du commerçant.
//     * @param model    Le modèle Spring MVC.
//     * @return La redirection vers le tableau de bord si la connexion est réussie, sinon retourne la page de connexion avec un message d'erreur.
//     */
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password,Model model, RedirectAttributes redirectAttributes) {
//        System.out.println("test2");
//        Shopkeepers shopkeepers = proxiShopService.findByEmail(username);
//        if (shopkeepers != null && shopkeepers.getPassword().equals(password)) {
//            redirectAttributes.addFlashAttribute("shopkeepers", shopkeepers);
//            return "redirect:/shopkeeper/dashboard";
//        } else {
//            model.addAttribute("error", "Invalid credentials");
//            return "login";
//        }
//    }

    /**
     * Affiche le tableau de bord du commerçant.
     *
     * @param model       Le modèle Spring MVC.
     * @param authentication  Les infos de la session
     * @return La vue du tableau de bord.
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        Shopkeepers shopkeepers = proxiShopService.findByEmail(authentication.getName());
        BdCategories db = new BdCategories();
        BdProducts dbp = new BdProducts();
        try {
            List<ProductCategory> categoryList = db.getAllCategories(shopkeepers.getWebsiteName());
            model.addAttribute("categoryList", categoryList);
            List<ProductSubCategory> subCategoryList = db.getAllSubCategories(shopkeepers.getWebsiteName());
            model.addAttribute("subCategoryList", subCategoryList);
            List<Product> productList = dbp.getAllProducts(shopkeepers.getWebsiteName());
            model.addAttribute("productList", productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        model.addAttribute("shopkeepers", shopkeepers);
        return "dashboard";
    }
}