package com.courier.courierapp.service;

import com.courier.courierapp.dto.PackageDTO;
import com.courier.courierapp.model.*;
import com.courier.courierapp.model.Package;
import com.courier.courierapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DeliveryFeeRepository deliveryFeeRepository;  // Inject DeliveryFeeRepository

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RevenueService revenueService;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public List<Package> getAllPackagesForClient(Long userId) {
        return packageRepository.findBySenderIdOrRecipientId(userId, userId);
    }

    public Optional<Package> getPackageById(Long id) {
        return packageRepository.findById(id);
    }

    public List<Package> getAllPackagesByCompanyForEmployee() {
        // Get the currently logged-in employee
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the logged-in user's username

        // Find the employee by username
        Users employee = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + username));
        Employee emp = employeeRepository.findByUserId(employee.getId()).orElseThrow(() -> new RuntimeException("Employee not found: " + username));

        // Ensure the user is an EMPLOYEE
        if (!employee.getRole().equals(Role.EMPLOYEE) || !employee.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Access denied: Only employees can view company packages.");
        }

        // Get the employee's company ID
        Long companyId = emp.getCompany().getId();

        // Fetch all packages for this company
        return packageRepository.findByCompanyId(companyId);
    }



    public Package createPackage(PackageDTO packageDTO) {
        // Fetch the associated company
        Company company = companyRepository.findById(packageDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id " + packageDTO.getCompanyId()));

        // Fetch the DeliveryFee associated with the company
        DeliveryFee deliveryFee = deliveryFeeRepository.findByCompanyId(company.getId())
                .orElseThrow(() -> new RuntimeException("Delivery Fee not found for company id " + packageDTO.getCompanyId()));

        // Calculate the delivery fee based on the weight and delivery type
        BigDecimal finalPrice = calculateDeliveryFee(packageDTO.getWeight(), packageDTO.getDeliveryType(), deliveryFee);

        // Map DTO to entity
        Package pack = new Package();
        pack.setSenderId(packageDTO.getSenderId());
        pack.setRecipientId(packageDTO.getRecipientId());
        pack.setCourierId(packageDTO.getCourierId());
        pack.setDeliveryType(packageDTO.getDeliveryType());
        pack.setDeliveryAddress(packageDTO.getDeliveryAddress());
        pack.setWeight(packageDTO.getWeight());
        pack.setPrice(finalPrice);  // Set the final calculated price
        pack.setStatus(packageDTO.getStatus());
        pack.setCompanyId(company.getId());

        // Set the Delivery Fee ID automatically
        pack.setDeliveryFee(deliveryFee.getId());  // Here we are directly assigning the DeliveryFee object

        Package savedPackage = packageRepository.save(pack);

        revenueService.createRevenue(savedPackage);

        return savedPackage;
    }


    public Package updatePackage(Long id, PackageDTO packageDTO) {
        return packageRepository.findById(id).map(pack -> {

            // Fetch the associated company
            Company company = companyRepository.findById(packageDTO.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with id " + packageDTO.getCompanyId()));

            // Fetch the associated DeliveryFee using companyId
            DeliveryFee deliveryFee = deliveryFeeRepository.findByCompanyId(company.getId())
                    .orElseThrow(() -> new RuntimeException("Delivery Fee not found for company id " + packageDTO.getCompanyId()));

            // Log the fetched package and delivery fee for debugging
            System.out.println("Updating package with ID: " + id);
            System.out.println("Package details: " + pack);
            System.out.println("Delivery Fee details: " + deliveryFee);

            // Recalculate the delivery fee based on the updated weight and delivery type
            BigDecimal finalPrice = calculateDeliveryFee(packageDTO.getWeight(), packageDTO.getDeliveryType(), deliveryFee);

            // Update entity fields
            pack.setSenderId(packageDTO.getSenderId());
            pack.setRecipientId(packageDTO.getRecipientId());
            pack.setCourierId(packageDTO.getCourierId());
            pack.setDeliveryType(packageDTO.getDeliveryType());
            pack.setDeliveryAddress(packageDTO.getDeliveryAddress());
            pack.setWeight(packageDTO.getWeight());
            pack.setPrice(finalPrice);  // Set the recalculated price
            pack.setStatus(packageDTO.getStatus());
            pack.setCompanyId(company.getId());

            // Set the DeliveryFee entity, not just the ID
            pack.setDeliveryFee(deliveryFee.getId());  // Set the full DeliveryFee object, not just its ID

            // Save the updated package
            Package updatedPackage = packageRepository.save(pack);

            // Update revenue
            revenueService.updateRevenue(updatedPackage);

            return updatedPackage;
        }).orElseThrow(() -> new RuntimeException("Package not found with id " + id));
    }


    private BigDecimal calculateDeliveryFee(double weight, DeliveryType deliveryType, DeliveryFee deliveryFee) {
        BigDecimal calculatedFee;

        // Calculate the delivery fee based on the delivery type
        if (deliveryType == DeliveryType.OFFICE) {
            calculatedFee = deliveryFee.getPricePerKgOffice().multiply(BigDecimal.valueOf(weight));
        } else {
            calculatedFee = deliveryFee.getPricePerKgAddress().multiply(BigDecimal.valueOf(weight));
        }

        return calculatedFee;
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

    public List<Package> getAllPackagesByCompany(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        if(user.getRole().equals(Role.EMPLOYEE))
        {
            Employee employee = employeeRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            if(!Objects.equals(employee.getCompany().getId(), id)){
                throw new RuntimeException("Access denied: Only employees of the company can view company packages.");
            }
        }
        return packageRepository.findByCompanyId(id);
    }

    public List<Package> getAllPackagesByRecipient(Long id) {
        return packageRepository.findByRecipientId(id);
    }

    public List<Package> getAllPackagesBySender(Long id) {
        return packageRepository.findBySenderId(id);
    }
}
