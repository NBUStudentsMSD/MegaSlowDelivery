package com.courier.courierapp.controller;

import com.courier.courierapp.dto.OrderDTO;
import com.courier.courierapp.model.Order;
import com.courier.courierapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(status);
    }

    // Create a new order
    @PostMapping
    public Order createOrder(@RequestBody OrderDTO order) {
        return orderService.createOrder(order);
    }

    // Update an existing order
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }
    // Get orders by company
    @GetMapping("/company/{companyId}")
    public List<Order> getOrdersByCompany(@PathVariable Long companyId) {
        return orderService.getOrdersByCompany(companyId);
    }


    // Delete an order
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}