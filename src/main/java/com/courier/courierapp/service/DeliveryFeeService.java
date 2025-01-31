package com.courier.courierapp.service;

import com.courier.courierapp.model.DeliveryFee;
import com.courier.courierapp.repository.DeliveryFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryFeeService {

    @Autowired
    private DeliveryFeeRepository deliveryFeeRepository;

    // Save a new or updated delivery fee
    public DeliveryFee saveDeliveryFee(DeliveryFee deliveryFee) {
        return deliveryFeeRepository.save(deliveryFee);
    }

    // Get all delivery fees
    public List<DeliveryFee> getAllDeliveryFees() {
        return deliveryFeeRepository.findAll();
    }

    // Get a delivery fee by ID
    public Optional<DeliveryFee> getDeliveryFeeById(Long id) {
        return deliveryFeeRepository.findById(id);
    }

    // Delete a delivery fee by ID
    public void deleteDeliveryFee(Long id) {
        deliveryFeeRepository.deleteById(id);
    }
}
