package org.example.proxishop.service;

import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;
import org.example.proxishop.repository.ShopkeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopkeeperService {

    @Autowired
    private ShopkeeperRepository shopkeeperRepository;

    public void saveShopkeeper(Shopkeeper shopkeeper) {
        shopkeeperRepository.save(shopkeeper);
    }

    public Shopkeeper findByEmail(String email) {
        return shopkeeperRepository.findByEmail(email);
    }
}