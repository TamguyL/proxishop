package org.example.proxishop.controller;

import jakarta.servlet.http.HttpServlet;
import org.example.proxishop.service.ProxiShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController extends HttpServlet {

    @Autowired
    private ProxiShopService proxiShopService;

    /**
     * Affiche la page d'accueil de ProxiShop.
     *
     */
    @GetMapping("/proxishop")
    public String proxishop() {
        return "proxishop";
    }

    /**
     * Affiche la première page de choix de template.
     *
     */
    @GetMapping("/template_choice1")
    public String template_choice1() {
        return "template_choice1";
    }

    /**
     * Affiche la deuxième page de choix de template.
     *
     */
    @GetMapping("/template_choice2")
    public String template_choice2() {
        return "template_choice2";
    }

    /**
     * Affiche la page de contact.
     *
     */
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    /**
     * Affiche la page des mentions légales.
     *
     */
    @GetMapping("/mentions")
    public String mentions() {
        return "mentions";
    }

    /**
     * Affiche la page des cookies.
     *
     */
    @GetMapping("/cookies")
    public String cookies() {
        return "cookies";
    }

}
