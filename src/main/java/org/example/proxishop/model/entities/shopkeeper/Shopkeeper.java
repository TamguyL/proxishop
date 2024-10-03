package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;


@Getter
@Setter

public class Shopkeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private int siret;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String adress;
    private String profilePicture;


    public Shopkeeper() {
    }

    public Shopkeeper(int siret, String firstName, String lastName, String password, String email, String adress, String profilePicture) {
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.adress = adress;
        this.profilePicture = profilePicture;
    }

    public Shopkeeper(int siret, String firstName, String lastName, String email, String adress, String profilePicture) {
        this.siret = siret;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.adress = adress;
        this.profilePicture = profilePicture;
    }

    public Shopkeeper(Double id, int siret, String firstName, String lastName, String password, String email, String adress, String profilePicture) {
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
