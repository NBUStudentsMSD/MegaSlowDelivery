package com.courier.courierapp.controller;

import com.courier.courierapp.dto.EmployeePerformanceDTO;
import com.courier.courierapp.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
