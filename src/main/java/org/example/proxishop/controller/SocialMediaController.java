package org.example.proxishop.controller;

import org.example.proxishop.model.database.DatabaseManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/socialMedia")
public class SocialMediaController {

    /**
     * Gère la soumission des informations de médias sociaux.
     *
     * @param bddname   Le nom de la base de données.
     * @param x         Le lien Twitter.
     * @param instagram Le lien Instagram.
     * @param facebook  Le lien Facebook.
     * @param tiktok    Le lien TikTok.
     * @param whatsapp  Le lien WhatsApp.
     * @param model     Le modèle Spring MVC.
     * @return Le nom de la vue à afficher après la soumission.
     * @throws SQLException Si une erreur SQL se produit.
     */
    @PostMapping
    public String socialMedia(@RequestParam String bddname, @RequestParam String x, @RequestParam String instagram,
                              @RequestParam String facebook, @RequestParam String tiktok, @RequestParam String whatsapp,
                              Model model) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.insertSocialMedia(bddname, x, instagram, facebook, tiktok, whatsapp);
        model.addAttribute("bddname", bddname);
        return "index";
    }
}