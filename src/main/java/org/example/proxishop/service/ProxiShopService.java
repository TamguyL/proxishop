package org.example.proxishop.service;

import jakarta.transaction.Transactional;
import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.repository.ShopkeepersRepositoryProxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Transactional
    public void updatePhoto(int id, String profilePicture) {
        Shopkeepers shopkeeper = shopkeepersRepositoryProxi.findById(id);
        shopkeeper.setProfilePicture(profilePicture);
        shopkeepersRepositoryProxi.save(shopkeeper);
    }

    @Transactional
    public void updatePassword(int id, String encryptedPassword) {
        Shopkeepers shopkeepers = shopkeepersRepositoryProxi.findById(id);
        shopkeepers.setPassword(encryptedPassword);
        shopkeepersRepositoryProxi.save(shopkeepers);
    }

    public Shopkeepers findByEmail(String email) {
        return shopkeepersRepositoryProxi.findByEmail(email);
    }

    public Shopkeepers findByWebsiteName(String websiteName) {
        return shopkeepersRepositoryProxi.findByWebsiteName(websiteName);
    }

    public boolean existsByWebsiteName(String websiteName) {
        return shopkeepersRepositoryProxi.existsByWebsiteName(websiteName);
    }

    public boolean existsByEmail(String email) {
        return shopkeepersRepositoryProxi.existsByEmail(email);
    }

}
