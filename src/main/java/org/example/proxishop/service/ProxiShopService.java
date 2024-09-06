package org.example.proxishop.service;

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

    public Shopkeepers findByEmail(String email) {
        return shopkeepersRepositoryProxi.findByEmail(email);
    }
}
