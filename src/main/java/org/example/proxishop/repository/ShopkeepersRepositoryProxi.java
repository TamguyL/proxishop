package org.example.proxishop.repository;

import org.example.proxishop.model.entities.proxi.Shopkeepers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopkeepersRepositoryProxi extends JpaRepository<Shopkeepers, Long> {
    Shopkeepers findByEmail(String email);


}