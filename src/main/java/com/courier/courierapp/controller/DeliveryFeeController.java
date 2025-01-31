package com.courier.courierapp.controller;

import com.courier.courierapp.model.DeliveryFee;
import com.courier.courierapp.service.DeliveryFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/deliveryfees")
public class DeliveryFeeController {

    @Autowired
    private DeliveryFeeService deliveryFeeService;

    // Create a new delivery fee
    @PostMapping
    public ResponseEntity<DeliveryFee> createDeliveryFee(@RequestBody DeliveryFee deliveryFee) {
        DeliveryFee savedDeliveryFee = deliveryFeeService.saveDeliveryFee(deliveryFee);
        return ResponseEntity.ok(savedDeliveryFee);
    }

    // Get all delivery fees
    @GetMapping
    public List<DeliveryFee> getAllDeliveryFees() {
        return deliveryFeeService.getAllDeliveryFees();
    }

    // Get delivery fee by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryFee> getDeliveryFeeById(@PathVariable Long id) {
        Optional<DeliveryFee> deliveryFee = deliveryFeeService.getDeliveryFeeById(id);
        return deliveryFee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a delivery fee
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryFee> updateDeliveryFee(@PathVariable Long id, @RequestBody DeliveryFee updatedDeliveryFee) {
        Optional<DeliveryFee> existingDeliveryFee = deliveryFeeService.getDeliveryFeeById(id);
        if (existingDeliveryFee.isPresent()) {
            updatedDeliveryFee.setId(id);
            DeliveryFee savedDeliveryFee = deliveryFeeService.saveDeliveryFee(updatedDeliveryFee);
            return ResponseEntity.ok(savedDeliveryFee);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a delivery fee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryFee(@PathVariable Long id) {
        if (deliveryFeeService.getDeliveryFeeById(id).isPresent()) {
            deliveryFeeService.deleteDeliveryFee(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
