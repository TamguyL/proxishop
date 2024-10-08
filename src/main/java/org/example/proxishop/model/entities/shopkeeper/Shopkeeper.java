package org.example.proxishop.model.entities.shopkeeper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shopkeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String siret;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String adress;
    private String profilePicture;

}
