package org.example.proxishop.service;

import org.example.proxishop.model.proxi.Shopkeepers;
import org.example.proxishop.model.shopkeeper.Shopkeeper;
import org.example.proxishop.repository.ShopkeeperRepository;
import org.example.proxishop.repository.ShopkeepersRepositoryProxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataService {

    @Autowired
    private ShopkeepersRepositoryProxi shopkeepersRepositoryProxi;
    private ShopkeeperRepository shopkeeperRepository;

    public void saveDataProxi(String bddname, String firm_name, Double siret, String firstName, String lastName,
                         String email, String adress, String option) {
        Shopkeepers shopkeepers = new Shopkeepers();
        shopkeepers.setFirmName(firm_name);
        shopkeepers.setSiret(siret);
        shopkeepers.setFirstName(firstName);
        shopkeepers.setLastName(lastName);
        shopkeepers.setEmail(email);
        shopkeepers.setAdress(adress);
        shopkeepers.setWebSiteName(bddname);
        shopkeepers.setId_offer(Double.parseDouble(option));

        shopkeepersRepositoryProxi.save(shopkeepers);
    }

    public void saveData(Double siret, String firstName, String lastName,
                         String email, String adress, String profilePicture) {
        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setSiret(siret);
        shopkeeper.setFirstName(firstName);
        shopkeeper.setLastName(lastName);
        shopkeeper.setEmail(email);
        shopkeeper.setAdress(adress);
        shopkeeper.setProfilePicture(profilePicture);

        shopkeeperRepository.save(shopkeeper);
    }
}
