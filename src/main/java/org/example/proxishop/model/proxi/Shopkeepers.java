package org.example.proxishop.model.proxi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="shopkeepers")
public class Shopkeepers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private Double siret;
    private String firstName;
    private String lastName;
    private String email;
    private String webSiteName;
    private String firmName;
    private String adress;
    private Double id_offer;


    public Shopkeepers() {
    }

    public Shopkeepers(Double siret, String firstName, String lastName, String email, String webSiteName, String firmName, String adress, Double id_offer) {
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.webSiteName = webSiteName;
        this.firmName = firmName;
        this.adress = adress;
        this.id_offer = id_offer;
    }

    public Shopkeepers(Double id, Double siret, String firstName, String lastName, String email, String webSiteName, String firmName, String adress, Double id_offer) {
        this.id = id;
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.webSiteName = webSiteName;
        this.firmName = firmName;
        this.adress = adress;
        this.id_offer = id_offer;
    }
}
