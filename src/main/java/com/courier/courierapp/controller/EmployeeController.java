package com.courier.courierapp.controller;

import com.courier.courierapp.model.Employee;
import com.courier.courierapp.repository.OfficeRepository;
import com.courier.courierapp.repository.UsersRepository;
import com.courier.courierapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get an employee by ID
    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Create a new employee
    @PostMapping

    public Employee createEmployee(@RequestBody Map<String,Long> requestData) {
        Long userId = requestData.get("user_id");
        Long officeId = requestData.get("office_id");
        return employeeService.createEmployee(userId,officeId);
    }

    // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Long> updateData) {
        Employee updatedEmployee = employeeService.updateEmployee(id, updateData);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //update ex.employee role
    @PutMapping("/{id}/role")
    public ResponseEntity<Employee> updateEmployeeRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> updateData) {
        if (!updateData.containsKey("role")) {
            return ResponseEntity.badRequest().body(null);
        }
        Employee updatedEmployee = employeeService.updateEmployeeRole(id, updateData.get("role"));
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //update ex.employee username
    @PutMapping("/{id}/username")
    public ResponseEntity<Employee> updateEmployeeUsername(
            @PathVariable Long id,
            @RequestBody Map<String, String> updateData) {
        if (!updateData.containsKey("username")) {
            return ResponseEntity.badRequest().body(null);
        }
        Employee updatedEmployee = employeeService.updateEmployeeUsername(id, updateData.get("username"));
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //update ex.employee password
    @PutMapping("/{id}/password")
    public ResponseEntity<Employee> updateEmployeePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> updateData) {
        if (!updateData.containsKey("password")) {
            return ResponseEntity.badRequest().body(null); // Ensure "password" is in the payload
        }
        Employee updatedEmployee = employeeService.updateEmployeePassword(id, updateData.get("password"));
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
