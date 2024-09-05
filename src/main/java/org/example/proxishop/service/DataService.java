package org.example.proxishop.service;

import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.example.proxishop.repository.ShopkeepersRepositoryProxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    @Autowired
    private ShopkeepersRepositoryProxi shopkeepersRepositoryProxi;

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
        shopkeepers.setId_offer(Integer.parseInt(option));

        shopkeepersRepositoryProxi.save(shopkeepers);
    }
}
