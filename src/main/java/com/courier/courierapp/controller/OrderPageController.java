package com.courier.courierapp.controller;

import com.courier.courierapp.model.Order;
import com.courier.courierapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderPageController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String getOrderPage(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-list";  // Name of the Thymeleaf template (order-list.html)
    }
}