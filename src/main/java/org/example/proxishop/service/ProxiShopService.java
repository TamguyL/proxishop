package org.example.proxishop.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.repository.ShopkeepersRepositoryProxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProxiShopService {

    @Autowired
    private ShopkeepersRepositoryProxi shopkeepersRepositoryProxi;

    public void saveShopkeeper(Shopkeepers shopkeepers) {
        shopkeepersRepositoryProxi.save(shopkeepers);
    }

    @Transactional
    public void updateShopkeeper(int id, String website_name, int id_offer) {
        // Récupérer l'entité existante de la base de données
        Shopkeepers shopkeeper = shopkeepersRepositoryProxi.findById(id);
        // Mettre à jour les attributs de l'entité existante
        shopkeeper.setWebsiteName(website_name);
        shopkeeper.setId_offer(id_offer);

        // Enregistrer l'entité mise à jour dans la base de données
        shopkeepersRepositoryProxi.save(shopkeeper);
    }

    @Transactional
    public void updatePersonnel(int id, String firstName, String lastName, String email) {
        Shopkeepers shopkeeper = shopkeepersRepositoryProxi.findById(id);
        shopkeeper.setFirstName(firstName);
        shopkeeper.setLastName(lastName);
        shopkeeper.setEmail(email);
        shopkeepersRepositoryProxi.save(shopkeeper);
    }

    @Transactional
    public void updateEntreprise(int id, String siret, String firm_name,  String adress) {
        Shopkeepers shopkeeper = shopkeepersRepositoryProxi.findById(id);
        shopkeeper.setSiret(siret);
        shopkeeper.setFirmName(firm_name);
        shopkeeper.setAdress(adress);
        shopkeepersRepositoryProxi.save(shopkeeper);
    }

    public Shopkeepers findByEmail(String email) {
        return shopkeepersRepositoryProxi.findByEmail(email);
    }

    public boolean existsByWebsiteName(String websiteName) {
        return shopkeepersRepositoryProxi.existsByWebsiteName(websiteName);
    }

    public boolean existsByEmail(String email) {
        return shopkeepersRepositoryProxi.existsByEmail(email);
    }

}
