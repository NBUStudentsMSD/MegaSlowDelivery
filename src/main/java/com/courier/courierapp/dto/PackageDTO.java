package com.courier.courierapp.dto;

import com.courier.courierapp.model.DeliveryType;
import com.courier.courierapp.model.PackageStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PackageDTO {
        private Long id;
        private Long senderId;
        private Long recipientId;
        private Long courierId;
        private DeliveryType deliveryType;
        private String deliveryAddress;
        private double weight;
        private BigDecimal price;
        private PackageStatus status;
        private LocalDateTime createdAt;
        private Long company_id; // Explicitly named as company_id

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public Long getRecipientId() {
            return recipientId;
        }

        public void setRecipientId(Long recipientId) {
            this.recipientId = recipientId;
        }

        public Long getCourierId() {
            return courierId;
        }

        public void setCourierId(Long courierId) {
            this.courierId = courierId;
        }

        public DeliveryType getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(DeliveryType deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }



        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public PackageStatus getStatus() {
            return status;
        }

        public void setStatus(PackageStatus status) {
            this.status = status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public Long getCompany_id() {
            return company_id;
        }

        public void setCompany_id(Long company_id) {
            this.company_id = company_id;
        }
    }

