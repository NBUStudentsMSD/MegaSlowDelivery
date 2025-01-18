package com.courier.courierapp.service;

import com.courier.courierapp.dto.OrderDTO;
import com.courier.courierapp.model.Company;
import com.courier.courierapp.model.Order;
import com.courier.courierapp.repository.CompanyRepository;
import com.courier.courierapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CompanyRepository companyRepository;

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get an order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    // Create a new order
    public Order createOrder(OrderDTO orderDTO) {
        Company company = companyRepository.findById(orderDTO.getCompany_id())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setAddress(orderDTO.getAddress());
        order.setDeliveryDate(orderDTO.getDeliveryDate());
        order.setStatus(orderDTO.getStatus());
        order.setOrderType(orderDTO.getOrderType());
        order.setCompany(company);

        return orderRepository.save(order);
    }

    // Update an order
    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setCustomerName(updatedOrder.getCustomerName());
            order.setAddress(updatedOrder.getAddress());
            order.setDeliveryDate(updatedOrder.getDeliveryDate());
            order.setStatus(updatedOrder.getStatus());
            return orderRepository.save(order);
        }).orElse(null);
    }
    public List<Order> getOrdersByCompany(Long companyId) {
        return orderRepository.findByCompanyId(companyId);
    }


    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}