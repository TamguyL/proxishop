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
public class Customize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int template;
    private String logo;
    private String businessDescription;

}
