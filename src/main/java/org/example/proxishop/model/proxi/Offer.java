package org.example.proxishop.model.proxi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Double id;
    private String OfferName;
    private Double VATFreePrice;

    public Offer() {
    }

    public Offer(String offerName, Double VATFreePrice) {
        OfferName = offerName;
        this.VATFreePrice = VATFreePrice;
    }

    public Offer(Double id, String offerName, Double VATFreePrice) {
        this.id = id;
        OfferName = offerName;
        this.VATFreePrice = VATFreePrice;
    }
}
