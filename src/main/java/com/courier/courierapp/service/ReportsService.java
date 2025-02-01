package com.courier.courierapp.service;

import com.courier.courierapp.dto.EmployeePerformanceDTO;
import com.courier.courierapp.model.Employee;
import com.courier.courierapp.model.Package;
import com.courier.courierapp.model.PackageStatus;
import com.courier.courierapp.repository.EmployeeRepository;
import com.courier.courierapp.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PackageRepository packageRepository;

    public List<EmployeePerformanceDTO> getEmployeePerformanceReports() {
        // 1) Вземаме всички служители
        List<Employee> employees = employeeRepository.findAll();

        // 2) Вземаме всички пакети (за да можем да ги филтрираме)
        List<Package> allPackages = packageRepository.findAll();

        // 3) Строим списъка DTO обекти
        List<EmployeePerformanceDTO> results = new ArrayList<>();

        for (Employee e : employees) {
            Long userId = e.getUser().getId(); // user_id на служителя

            // Броим колко пакети имат courierId = userId
            long count = allPackages.stream()
                    .filter(p -> p.getCourierId() != null && p.getCourierId().equals(userId))
                    .count();

            // Създаваме DTO
            EmployeePerformanceDTO dto = new EmployeePerformanceDTO();
            dto.setEmployeeId(e.getId());
            dto.setEmployeeUsername(e.getUser().getUsername());
            dto.setTotalPackages(count);

            results.add(dto);
        }

        return results;
    }

    public List<Package> getUnreceivedPackages() {
        return packageRepository.findByStatus(PackageStatus.SENT);
    }

    public BigDecimal getCompanyRevenue(Long companyId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // Fetch all matching packages
        List<Package> packages = packageRepository.findByCompanyIdAndCreatedAtBetween(companyId, startDateTime, endDateTime);

        // Sum up the revenue
        return packages.stream()
                .map(Package::getPrice) // Extract price
                .filter(price -> price != null) // Avoid null values
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum the prices
    }
}