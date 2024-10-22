package org.example.proxishop.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.example.proxishop.model.database.shopkeeper.BdProducts;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.model.entities.shopkeeper.Product;
import org.example.proxishop.service.ProxiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Controller

public class ProductController {


    @Autowired
    private ProxiShopService proxiShopService;

    @GetMapping("/products")
    public String showProducts(Model model) {
        return "products";
    }



//------INSERT-----PRODUCTS---------------------------------------------------------------------------------------------

    @PostMapping("/products/newProduct")
    public String showNewProductForm(@RequestParam int id_subcategory, Model model) {
        model.addAttribute("id_subcategory", id_subcategory);
        return "addProductForm";
    }

    @PostMapping("/products/addProducts")
    public String addProducts(@RequestParam int id_subcategory, @RequestParam String productName,
                              @RequestParam String description, @RequestParam double price, @RequestParam int stock, @RequestParam MultipartFile file,
                              Model model, RedirectAttributes redirectAttributes) throws SQLException {




        // Récupère l'objet Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Récupération du nom de BDD à partir de l'objet Authentication
        String website_name = getDatabaseNameFromAuthentication(authentication);


        // Vérifier que le nom de la base de données n'est pas null ou vide
        if (website_name == null || website_name.isEmpty()) {

            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        Integer idProduct = null; //Mettre l'id du produit reçu en paramètre ou null si addProduct
        String imageUrl;
        try {
            imageUrl = saveProductPicture(file, idProduct, website_name);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            return "addProducts";// adress de retour si image non valide
        }

        // Insérer le nouveau produit
        BdProducts db = new BdProducts();
        db.insertNewProduct(id_subcategory, website_name, productName, description, price, stock, imageUrl);

        // Ajouter le nom de la base de données au modèle et aux attributs de redirection
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);

        return "redirect:/shopkeeper/dashboard";
    }



//--!----UPDATE-----PRODUCTS--------------------------------------------------------------------------------------------


    @PostMapping("/products/updateProduct")
    public String updateProducts(@RequestParam int productid, @RequestParam int id_subcategory, @RequestParam String productName,
                                @RequestParam String description, @RequestParam double price,@RequestParam int stock,
                                @RequestParam MultipartFile file,Model model, RedirectAttributes redirectAttributes) throws SQLException {

        // Récupère l'objet Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Récupération du nom de BDD à partir de l'objet Authentication
        String website_name = getDatabaseNameFromAuthentication(authentication);


        // Vérifie si le nom de la BDD n'est pas "null" ou vide
        if (website_name == null || website_name.isEmpty()) {

            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        Integer idProduct = productid; //Mettre l'id du produit reçu en paramètre ou null si addProduct
        String imageUrl;
        try {
            imageUrl = saveProductPicture(file, idProduct, website_name);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/shopkeeper/dashboard";// adress de retour si image non valide
        }
//        model.addAttribute("idproduct", productid);
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);

        BdProducts db = new BdProducts();
        db.updateProduct(productid,id_subcategory,website_name,productName,description,price,stock,imageUrl);
        return "redirect:/shopkeeper/dashboard";
    }

//-----DELETE---PRODUCTS-----------------------------------------------------------------------------------------------
    @PostMapping("/products/deleteProduct")
    public String deleteProduct(@RequestParam int productid, @RequestParam String productName,
                              Model model, RedirectAttributes redirectAttributes) throws SQLException {
        // Récupérer l'objet Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        // Récupérer le nom de la base de données à partir de l'objet Authentication
        String website_name = getDatabaseNameFromAuthentication(authentication);


        // Vérifie si le nom de la BDD n'est pas "null" ou vide
        if (website_name == null || website_name.isEmpty()) {
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        //Appelle la méthode deleteProduct de BdProducts et lui transmets les paramètres
        BdProducts db = new BdProducts();
        db.deleteProduct(productid, website_name, productName);

        // Ajoute le nom de la BDD au modèle et aux attributs de redirection
        model.addAttribute("website_name", website_name);
        redirectAttributes.addFlashAttribute("website_name", website_name);

        return "redirect:/shopkeeper/dashboard";
    }

    private String getDatabaseNameFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Shopkeepers shopkeepers = proxiShopService.findByEmail(authentication.getName());
            if (shopkeepers != null) {
                return shopkeepers.getWebsiteName();
            }
        }
        return null;
    }


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