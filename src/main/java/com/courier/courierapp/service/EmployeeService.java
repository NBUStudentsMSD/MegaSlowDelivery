package com.courier.courierapp.service;

import com.courier.courierapp.model.Employee;
import com.courier.courierapp.model.Office;
import com.courier.courierapp.repository.EmployeeRepository;
import com.courier.courierapp.repository.OfficeRepository;
import com.courier.courierapp.repository.UsersRepository;
import com.courier.courierapp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setUser(updatedEmployee.getUser());
            employee.setOffice(updatedEmployee.getOffice());
            return employeeRepository.save(employee);
        }).orElse(null);
    }

    // Delete an employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}

