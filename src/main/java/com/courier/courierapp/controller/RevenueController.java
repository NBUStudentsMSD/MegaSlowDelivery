package com.courier.courierapp.controller;


import com.courier.courierapp.model.Package;
import com.courier.courierapp.model.Revenue;
import com.courier.courierapp.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/revenues")
public class RevenueController {

    private final RevenueService revenueService;

    @Autowired
    public RevenueController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @GetMapping
    public List<Revenue> getAllRevenues() {
        return revenueService.getAllRevenues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revenue> getRevenueById(@PathVariable Long id) {
        return ResponseEntity.ok(revenueService.getRevenueById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevenue(@PathVariable Long id) {
        revenueService.deleteRevenue(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{id}/date-range/sum")
    public Map<LocalDate, BigDecimal> getRevenuesSumByDateRange(@RequestParam String from, @RequestParam String to, @PathVariable Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        return revenueService.getRevenuesSumByDateRange(fromDate, toDate, id);
    }
}

