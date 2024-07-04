package org.example.proxishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/newbdd")
    public String newbdd() {
        return "";
    }
}
