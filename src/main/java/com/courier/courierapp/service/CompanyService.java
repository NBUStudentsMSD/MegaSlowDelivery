package com.courier.courierapp.service;


import com.courier.courierapp.model.*;
import com.courier.courierapp.repository.CompanyRepository;
import com.courier.courierapp.repository.EmployeeRepository;
import com.courier.courierapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long userId = null;

        Users user = usersRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + authentication.getName()));
        if(user.getRole() != Role.EMPLOYEE){
            return companyRepository.findAll();
        }else {
            Employee employee = employeeRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            return List.of(employee.getCompany());
        }
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
