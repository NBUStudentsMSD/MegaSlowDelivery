package com.courier.courierapp.controller;

import com.courier.courierapp.dto.EmployeePerformanceDTO;
import com.courier.courierapp.model.Package;
import com.courier.courierapp.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    // GET /api/reports/employees
    @GetMapping("/employees")
    public List<EmployeePerformanceDTO> getEmployeePerformance() {
        return reportsService.getEmployeePerformanceReports();
    }

    // All packages sent but not received
    @GetMapping("/unreceived")
    public List<Package> getUnreceivedPackages() {
        return reportsService.getUnreceivedPackages();
    }

    @GetMapping("/revenue")
    public BigDecimal getCompanyRevenue(
            @RequestParam Long companyId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return reportsService.getCompanyRevenue(companyId, startDate, endDate);
    }
}


