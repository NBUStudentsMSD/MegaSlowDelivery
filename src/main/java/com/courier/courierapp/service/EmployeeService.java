package com.courier.courierapp.service;

import com.courier.courierapp.model.Employee;
import com.courier.courierapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get an employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Create a new employee
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
//
//    // Update an existing employee
//    public Employee updateEmployee(Long id, Employee updatedEmployee) {
//        return employeeRepository.findById(id).map(employee -> {
//            employee.setUser(updatedEmployee.getUser());
//            employee.setOffice(updatedEmployee.getOffice());
//            return employeeRepository.save(employee);
//        }).orElse(null);
//    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}

