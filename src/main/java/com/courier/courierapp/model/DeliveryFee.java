package com.courier.courierapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "delivery_fees")
public class DeliveryFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name= "price_per_kg" ,nullable = false)
    private double weightPerKg;

    @Column(nullable = false, name = "price_office")
    private BigDecimal pricePerKgOffice; // Cheaper for office delivery

    @Column(nullable = false, name = "price_address")
    private BigDecimal pricePerKgAddress; // More expensive for address delivery

    // Constructors
    public DeliveryFee() {}

    public DeliveryFee(Long id, Long companyId, double weightPerKg, BigDecimal pricePerKgOffice, BigDecimal pricePerKgAddress) {
        this.id = id;
       this.companyId = companyId;
        this.weightPerKg = weightPerKg;
        this.pricePerKgOffice = pricePerKgOffice;
        this.pricePerKgAddress = pricePerKgAddress;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompany_id() {
        return companyId;
    }

    public void setCompany_id(Long companyId) {
        this.companyId = companyId;
    }

    public double getWeightPerKg() {
        return weightPerKg;
    }

    public void setWeightPerKg(double weightPerKg) {
        this.weightPerKg = weightPerKg;
    }

    public BigDecimal getPricePerKgOffice() {
        return pricePerKgOffice;
    }

    public void setPricePerKgOffice(BigDecimal pricePerKgOffice) {
        this.pricePerKgOffice = pricePerKgOffice;
    }

    public BigDecimal getPricePerKgAddress() {
        return pricePerKgAddress;
    }

    public void setPricePerKgAddress(BigDecimal pricePerKgAddress) {
        this.pricePerKgAddress = pricePerKgAddress;
    }
}