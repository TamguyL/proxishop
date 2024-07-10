package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

@Profile("!disableJpa")
@Getter
@Setter
@Entity
public class Shopkeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private Double siret;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String adress;
    private String profilePicture;


    public Shopkeeper() {
    }

    public Shopkeeper(Double siret, String firstName, String lastName, String password, String email, String adress, String profilePicture) {
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.adress = adress;
        this.profilePicture = profilePicture;
    }

    public Shopkeeper(Double id, Double siret, String firstName, String lastName, String password, String email, String adress, String profilePicture) {
        this.id = id;
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.adress = adress;
        this.profilePicture = profilePicture;
    }

}
