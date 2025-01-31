package com.courier.courierapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "delivery_fees")
public class DeliveryFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name= "price_per_kg" ,nullable = false)
    private double weightPerKg;

    @Column(nullable = false, name = "price_office")
    private BigDecimal pricePerKgOffice; // Cheaper for office delivery

    @Column(nullable = false, name = "price_address")
    private BigDecimal pricePerKgAddress; // More expensive for address delivery

    // Constructors
    public DeliveryFee() {}

    public DeliveryFee(Company company, double weightPerKg, BigDecimal pricePerKgOffice, BigDecimal pricePerKgAddress) {
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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