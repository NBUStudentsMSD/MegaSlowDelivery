package com.courier.courierapp.dto;

public class EmployeePerformanceDTO {
    private Long employeeId;
    private String employeeUsername;
    private long totalPackages;

    // Constructors, Getters, Setters
    public EmployeePerformanceDTO() {}

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public long getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(long totalPackages) {
        this.totalPackages = totalPackages;
    }
}
