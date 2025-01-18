package com.courier.courierapp.service;

import com.courier.courierapp.dto.CreateUserDTO;
import com.courier.courierapp.model.Company;
import com.courier.courierapp.model.Role;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.repository.CompanyRepository;
import com.courier.courierapp.repository.UsersRepository;
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

    //Get all existing users
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    //Get a user by ID
    public Optional<Users> getUserById(Long id){
        return usersRepository.findById(id);
    }
    public Users createUser(CreateUserDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + dto.getCompanyId()));

        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setCompany(company);

        return usersRepository.save(user);
    }

    //update an existing user
    public Users updateUser(Long id, Users updatedUser) {
        return usersRepository.findById(id).map(user -> {
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
