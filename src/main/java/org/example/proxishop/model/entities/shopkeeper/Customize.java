package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double template;
    private String logo;
    private String businessDescription;


    public Customize(int id, Double template, String logo, String businessDescription) {
        this.id = id;
        this.template = template;
        this.logo = logo;
        this.businessDescription = businessDescription;
    }



    public Customize(Double template, String logo, String businessDescription) {
        this.template = template;
        this.logo = logo;
        this.businessDescription = businessDescription;
}

public Customize() {};
}
