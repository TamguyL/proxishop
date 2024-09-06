package org.example.proxishop.repository;

import org.example.proxishop.model.entities.shopkeeper.Shopkeeper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopkeeperRepository extends JpaRepository<Shopkeeper, Double> {
    Shopkeeper findByEmail(String email);
}