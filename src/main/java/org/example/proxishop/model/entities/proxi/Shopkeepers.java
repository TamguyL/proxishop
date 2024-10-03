package org.example.proxishop.model.entities.proxi;

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
    private int id;
    private Double siret;
    private String firstName;
    private String lastName;
    private String email;
    private String websiteName;
    private String firmName;
    private String adress;
    private int id_offer;
    private String password;
    private String profilePicture;

    public Shopkeepers() {
    }

    public Shopkeepers(String firstName, String lastName, String firmName, String adress, Double siret, String email, String password, String profilePicture) {
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.firmName = firmName;
        this.adress = adress;
        this.password = password;
        this.profilePicture = profilePicture;

    }

    public Shopkeepers(int id, Double siret, String firstName, String lastName, String email, String websiteName, String firmName, String adress, int id_offer, String profilePicture) {
        this.id = id;
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.websiteName = websiteName;
        this.firmName = firmName;
        this.adress = adress;
        this.id_offer = id_offer;
        this.profilePicture = profilePicture;
    }

}
