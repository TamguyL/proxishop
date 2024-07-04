package org.example.proxishop.model.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String x;
    private String insta;
    private String fb;
    private String tiktok;
    private String whatsapp;

    public SocialMedia(Double id, String x, String insta, String fb, String tiktok, String whatsapp) {
        this.id = id;
        this.x = x;
        this.insta = insta;
        this.fb = fb;
        this.tiktok = tiktok;
        this.whatsapp = whatsapp;
    }

    public SocialMedia( String x, String insta, String fb, String tiktok, String whatsapp) {
        this.x = x;
        this.insta = insta;
        this.fb = fb;
        this.tiktok = tiktok;
        this.whatsapp = whatsapp;
}

public SocialMedia() {}
}
