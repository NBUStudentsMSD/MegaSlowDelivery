package com.courier.courierapp.service;

import com.courier.courierapp.dto.UserDTO;
import com.courier.courierapp.model.*;
import com.courier.courierapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OfficeRepository officeRepository;

    //Get all existing users
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    //Get a user by ID
    public Optional<Users> getUserById(Long id){
        return usersRepository.findById(id);
    }

    public Users createUser(UserDTO dto) {
        // 1) Намираме компания
        Company company = null;
        if(dto.getCompany_id() != null) {
            company = companyRepository.findById(dto.getCompany_id())
                    .orElseThrow(() -> new RuntimeException("Company not found with ID: " + dto.getCompany_id()));
        }

        // 2) Създаваме Users
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        Users savedUser = usersRepository.save(user);

        // Създаване на Client
        if (dto.getRole() == Role.CLIENT && company != null) {
            Client client = new Client();
            client.setUser(savedUser);
            client.setCompany(company);
            clientRepository.save(client);
        }

        // Създаване на Employee
        if (dto.getRole() == Role.EMPLOYEE && company != null) {
            if (dto.getOffice_id() == null) {
                throw new RuntimeException("office_id is required for EMPLOYEE");
            }
            Office office = officeRepository.findById(dto.getOffice_id())
                    .orElseThrow(() -> new RuntimeException("Office not found with ID: " + dto.getOffice_id()));

            Employee employee = new Employee();
            employee.setUser(savedUser);
            employee.setCompany(company);
            employee.setOffice(office);
            employee.setEmployeeType(dto.getEmployeeType());
            employeeRepository.save(employee);
        }

        return savedUser;  // връщаме самия Users обект
    }

    //update an existing user
    public Users updateUser(Long id, UserDTO updatedUser) {
        return usersRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return usersRepository.save(user);
        }).orElse(null);
    }


    //delete a user
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}