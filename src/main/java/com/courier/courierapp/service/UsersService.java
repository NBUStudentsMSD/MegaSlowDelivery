package com.courier.courierapp.service;

import com.courier.courierapp.dto.UserDTO;
import com.courier.courierapp.model.*;
import com.courier.courierapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private ClientRepository clientRepository;  // Assuming you have a ClientRepository
    @Autowired
    private EmployeeRepository employeeRepository;  // Assuming you have an EmployeeRepository

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Password encoder
    //Get all existing users
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    public Users createUser(UserDTO dto) {
        try {
            // Get the company
            Company company = companyRepository.findById(dto.getCompany_id())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            // Create the user
            Users user = new Users();
            user.setUsername(dto.getUsername());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));  // Hash the password
            user.setRole(dto.getRole());
            user.setCompany(company); // Set the company for the user

            // Save the user to the database
            user = usersRepository.save(user);

            // Depending on the role, save the user in the respective table (client or employee)
            if (dto.getRole() == Role.CLIENT) {
                Client client = new Client();
                client.setUser(user);  // Link the client to the user
                client.setCompany(company); // Set the company for the client
                clientRepository.save(client);  // Save the client
            } else if (dto.getRole() == Role.EMPLOYEE) {
                // Ensure that an office is linked to the employee
                Office office = officeRepository.findById(dto.getOffice_id())
                        .orElseThrow(() -> new RuntimeException("Office not found"));

                Employee employee = new Employee();
                employee.setUser(user);  // Link the employee to the user
                employee.setCompany(company); // Set the company for the employee
                employee.setOffice(office);  // Set the office for the employee
                employeeRepository.save(employee);  // Save the employee
            }

            return user;  // Return the created user
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception stack trace for debugging
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }






    //Get a user by ID
    public Optional<Users> getUserById(Long id){
        return usersRepository.findById(id);
    }

    //update an existing user
    public Users updateUser(Long id, UserDTO updatedUser) {
        return usersRepository.findById(id).map(user -> {
            Company company = companyRepository.findById(updatedUser.getCompany_id())
                    .orElseThrow(() -> new RuntimeException("Company not found with ID: " + updatedUser.getCompany_id()));
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return usersRepository.save(user);
        }).orElse(null);
    }
    public List<Users> getUsersByCompany(Long companyId) {
        return usersRepository.findByCompanyId(companyId);
    }

    //delete a user
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
