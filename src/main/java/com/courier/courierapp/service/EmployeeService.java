package com.courier.courierapp.service;

import com.courier.courierapp.model.Employee;
import com.courier.courierapp.model.Office;
import com.courier.courierapp.model.Role;
import com.courier.courierapp.repository.EmployeeRepository;
import com.courier.courierapp.repository.OfficeRepository;
import com.courier.courierapp.repository.UsersRepository;
import com.courier.courierapp.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private UsersRepository usersRepository;

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get an employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Create a new employee
    public Employee createEmployee(Long userId,Long officeId) {
        if(userId==null || officeId==null){
            throw new RuntimeException("User ID and Office ID are required");
        }
        // Fetch the User entity
        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the Office entity
        Office office = officeRepository.findById(officeId).orElseThrow(() -> new RuntimeException("Office not found"));

        Employee employee=new Employee();
        employee.setUser(user);
        employee.setOffice(office);
        return employeeRepository.save(employee);
    }

    // Update an existing employee
    public Employee updateEmployee(Long id, Map<String, Long> updateData) {
        return employeeRepository.findById(id).map(employee -> {
            // Fetch and update the user if user_id is provided
            if (updateData.containsKey("user_id")) {
                Long userId = updateData.get("user_id");
                usersRepository.findById(userId).ifPresent(employee::setUser);
            }

            // Fetch and update the office if office_id is provided
            if (updateData.containsKey("office_id")) {
                Long officeId = updateData.get("office_id");
                officeRepository.findById(officeId).ifPresent(employee::setOffice);
            }

            // Save and return the updated employee
            return employeeRepository.save(employee);
        }).orElse(null); // Return null
    }
    public Employee updateEmployeeRole(Long id, String role) {
        return employeeRepository.findById(id).map(employee -> {
            // Update the role of the employee's user
            Users user = employee.getUser();
            if (user != null) {
                user.setRole(Role.valueOf(role));
            }
            return employeeRepository.save(employee);
        }).orElse(null); // Return null
    }

    //update username
    public Employee updateEmployeeUsername(Long id, String username) {
        return employeeRepository.findById(id).map(employee -> {
            // Update the username of the employee's user
            Users user = employee.getUser();
            if (user != null) {
                user.setUsername(username);
            }
            return employeeRepository.save(employee);
        }).orElse(null); // Return null

    }

    //update password
    public Employee updateEmployeePassword(Long id, String password) {
        return employeeRepository.findById(id).map(employee -> {
            // Update the password of the employee's user
            Users user = employee.getUser();
            if (user != null) {
                user.setPassword(password);
            }
            return employeeRepository.save(employee);
        }).orElse(null); // Return null if the employee is not found
    }



    // Delete an employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}

