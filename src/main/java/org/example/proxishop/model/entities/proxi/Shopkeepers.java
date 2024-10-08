package org.example.proxishop.model.entities.proxi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="shopkeepers")
public class Shopkeepers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String siret;
    private String firstName;
    private String lastName;
    private String email;
    private String websiteName;
    private String firmName;
    private String adress;
    private int id_offer;
    private String password;
    private String profilePicture;

}
