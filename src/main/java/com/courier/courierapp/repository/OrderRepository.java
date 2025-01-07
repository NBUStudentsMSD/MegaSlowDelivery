package com.courier.courierapp.repository;

import com.courier.courierapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom query to find orders by status
    List<Order> findByStatus(String status);
}