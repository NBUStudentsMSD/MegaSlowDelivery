package com.courier.courierapp.controller;

import com.courier.courierapp.dto.EmployeeDTO;
import com.courier.courierapp.model.Employee;
import com.courier.courierapp.model.Role;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.EmployeeRepository;
import com.courier.courierapp.repository.OfficeRepository;
import com.courier.courierapp.repository.UsersRepository;
import com.courier.courierapp.service.EmployeeService;
import com.courier.courierapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


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
    public Employee createEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.createEmployee(employee);
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
    @GetMapping("/company/{companyId}")
    public List<Employee> getEmployeesByCompany(@PathVariable Long companyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users currentUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        if(currentUser.getRole() == Role.EMPLOYEE){
            Employee employee = employeeRepository.findByUserId(currentUser.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            if(employee.getCompany().getId() != companyId){
                throw new RuntimeException("You are not allowed to see employees from other companies");
            }
        }
        return employeeService.getEmployeesByCompany(companyId);
    }
    @GetMapping("/company/courier/{companyId}")
    public List<Employee> getCouriersByCompany(@PathVariable Long companyId) {
        return employeeService.getCouriersByCompany(companyId);
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
