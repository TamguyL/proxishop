package org.example.proxishop.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.example.proxishop.model.database.shopkeeper.BdCategories;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.shopkeeper.Product;
import org.example.proxishop.model.entities.shopkeeper.ProductCategory;
import org.example.proxishop.model.entities.shopkeeper.ProductSubCategory;
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
import java.util.List;

@Controller
@RequestMapping("/products")
@SessionAttributes("website_name")
public class ProductController {

    // Initialisation de l'attribut de session
    @ModelAttribute("website_name")
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
     * Affiche la page de création de produits.
     *
     * @param website_name Le nom de la base de données.
     * @param model   Le modèle Spring MVC.
     */
    @GetMapping
    public String creaProd(@ModelAttribute("website_name") String websit_name, Model model) {
        String website_name = "hypnooz";
        BdCategories db = new BdCategories();
        BdProducts dbp = new BdProducts();
        try {
            List<ProductCategory> categoryNamesList = db.getAllCategories(website_name);
            model.addAttribute("categoryNamesList", categoryNamesList);
            List<ProductSubCategory> subCategoryList = db.getAllSubCategories(website_name);
            model.addAttribute("subCategoryList", subCategoryList);
            List<Product> productList = dbp.getAllProducts(website_name);
            model.addAttribute("productList", productList);
            model.addAttribute("bddname", website_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "products";
    }

    /**
     * Ajoute un nouveau produit à la base de données.
     *
     * @param subCategoryid L'ID de la sous-catégorie.
     * @param website_name       Le nom de la base de données.
     * @param productName   Le nom du produit.
     * @param description   La description du produit.
     * @param price         Le prix du produit.
     * @param stock         Le stock disponible du produit.
     * @param image         L'URL de l'image du produit.
     * @param model         Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/addProducts")
    public String addProducts(@RequestParam int subCategoryid, @RequestParam String website_name, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam int stock, @RequestParam String image,
                              Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdProducts db = new BdProducts();
        db.insertNewProduct(subCategoryid, website_name, productName, description, price, stock, image);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        return "redirect:/products";
    }

    /**
     * Met à jour un produit existant dans la base de données.
     *
     * @param subCategoryid L'ID de la sous-catégorie.
     * @param website_name       Le nom de la base de données.
     * @param productName   Le nom du produit.
     * @param description   La description du produit.
     * @param price         Le prix du produit.
     * @param stock         Le stock disponible du produit.
     * @param image         L'URL de l'image du produit.
     * @param id_product    L'ID du produit à mettre à jour.
     * @param model         Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/updateProducts")
    public String updateProducts(@RequestParam int subCategoryid, @RequestParam String website_name, @RequestParam String productName,
                                 @RequestParam String description, @RequestParam double price, @RequestParam int stock, @RequestParam String image,
                                 @RequestParam int id_product, Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdProducts db = new BdProducts();
        db.updateProduct(id_product, subCategoryid, website_name, productName, description, price, stock, image);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        return "redirect:/products";
    }

    /**
     * Supprime un produit de la base de données.
     *
     * @param website_name     Le nom de la base de données.
     * @param productName Le nom du produit.
     * @param id_product  L'ID du produit à supprimer.
     * @param model       Le modèle Spring MVC.
     * @return La redirection vers la page des produits.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping("/deleteProducts")
    public String deleteProducts(@RequestParam String website_name, @RequestParam String productName, @RequestParam int id_product,
                                 Model model, RedirectAttributes redirectAttributes) throws SQLException {
        BdProducts db = new BdProducts();
        db.deleteProduct(id_product, website_name, productName);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);
        return "redirect:/products";
    }


//    // A mettre dans la fonction AddProduct et UpdateProduct
//    // Ne pas oublier de remplacer String Image par MultipartFile file
//    // Mettre dans <form ... enctype="multipart/form-data">
//    //        <label for="file">Choisir une image produit:</label>
//    //        <input type="file" id="file" name="file" accept="image/png, image/jpeg, image/gif" required/><br>
//    Integer idProduct = id_product; //Mettre l'id du produit reçu en paramètre ou null si addProduct
//    String imageUrl;
//        try {
//        imageUrl = saveProductPicture(file, idProduct, website_name);
//    } catch (IOException e) {
//        model.addAttribute("error", e.getMessage());
//        return "addProducts";// adress de retour si image non valide
//    }
    public String saveProductPicture(MultipartFile file, Integer id_product, String websiteName) throws IOException, SQLException {
        // Verifier comment retrouver l'id du produit
        if (id_product != null) {
            BdProducts db = new BdProducts();
            // Bien verifier que getProductById existe
            Product product = db.getProductById(websiteName, id_product);
            if (product.getImage() != null) {
                String oldImagePath = System.getProperty("user.dir") + "/src/main/resources/static" + product.getImage();
                File oldImageFile = new File(oldImagePath);
                if (oldImageFile.exists()) {
                    if (oldImageFile.delete()) {
                        System.out.println("Ancienne photo supprimée avec succès.");
                    } else {
                        throw new IOException("Échec de la suppression de l'ancienne photo.");
                    }
                }
            }
        }

        // Vérifie que file n'est pas vide
        if (file.isEmpty()) {
            throw new IOException("L'image du produit est vide");
        }

        // Vérifie format de l'image
        String contentType = file.getContentType();
        if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
            throw new IOException("Le format de l'image doit être PNG, JPG ou GIF");
        }

        // Lis l'image et vérifie si l'image est valide
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new IOException("Le fichier n'est pas une image valide");
        }

        // Vérifie et redimensionne l'image si elle dépasse 500x500
        if (image.getWidth() > 500 || image.getHeight() > 500) {
            // Utilisation de Thumbnailator pour redimensionner l'image tout en conservant le ratio
            image = Thumbnails.of(image)
                    .size(500, 500) // Taille maximale
                    .asBufferedImage(); // Retourne l'image redimensionnée en mémoire
        }

        // Récupérer le nom original du fichier et son extension
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        // Extraire l'extension du fichier (après le dernier point)
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // Génère un nom unique pour le fichier avec un timestamp
        String uniqueFileName = System.currentTimeMillis() + fileExtension;

        // Sauvegarde l'image
        String destinationPath = System.getProperty("user.dir") + "/src/main/resources/static/uploads/products/" + uniqueFileName;
        File dest = new File(destinationPath);

        // Sauvegarde l'image redimensionnée avec Thumbnailator
        Thumbnails.of(image)
                .size(image.getWidth(), image.getHeight()) // Utilise les dimensions actuelles de l'image (redimensionnée si nécessaire)
                .outputFormat(fileExtension.replace(".", "")) // Format de sortie en fonction de l'extension d'origine
                .toFile(dest); // Sauvegarde dans le fichier de destination

        // Retourne le chemin relatif de l'image
        return "/uploads/products/" + uniqueFileName;
    }

}