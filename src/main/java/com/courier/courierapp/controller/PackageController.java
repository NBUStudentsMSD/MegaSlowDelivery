package com.courier.courierapp.controller;

import com.courier.courierapp.dto.PackageDTO;
import com.courier.courierapp.model.Package;
import com.courier.courierapp.model.Role;
import com.courier.courierapp.model.Users;
import com.courier.courierapp.service.PackageService;
import com.courier.courierapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public List<Package> getAllPackages() {
        // Намираме username от SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Търсим в базата съответния Users
        Users currentUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // Ако е EMPLOYEE -> всички пакети
        if (currentUser.getRole() == Role.EMPLOYEE) {
            return packageService.getAllPackages();
        } else {
            // Ако е CLIENT -> само собствените пакети
            return packageService.getAllPackagesForClient(currentUser.getId());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        return packageService.getPackageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/companypackages")
    public List<Package> getAllPackagesForEmployeeCompany() {
        return packageService.getAllPackagesByCompanyForEmployee();
    }


    @PostMapping
    public Package createPackage(@RequestBody PackageDTO pack) {
        return packageService.createPackage(pack);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(@PathVariable Long id, @RequestBody PackageDTO packageDetails) {
        try {
            return ResponseEntity.ok(packageService.updatePackage(id, packageDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
    }
}
