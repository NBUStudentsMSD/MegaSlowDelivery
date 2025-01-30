package com.courier.courierapp.service;

import com.courier.courierapp.dto.PackageDTO;
import com.courier.courierapp.model.Company;
import com.courier.courierapp.model.DeliveryType;
import com.courier.courierapp.model.Package;
import com.courier.courierapp.repository.CompanyRepository;
import com.courier.courierapp.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RevenueService revenueService;

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    // New method to get all packages for a specific client
    public List<Package> getAllPackagesForClient(Long userId) {
        return packageRepository.findBySenderIdOrRecipientId(userId, userId);
    }

    public Optional<Package> getPackageById(Long id) {
        return packageRepository.findById(id);
    }

    public Package createPackage(PackageDTO packageDTO) {
        // Fetch the associated company
        Company company = companyRepository.findById(packageDTO.getCompany_id())
                .orElseThrow(() -> new RuntimeException("Company not found with id " + packageDTO.getCompany_id()));

        // Map DTO to entity
        Package pack = new Package();
        pack.setSenderId(packageDTO.getSenderId());
        pack.setRecipientId(packageDTO.getRecipientId());
        pack.setCourierId(packageDTO.getCourierId());
        pack.setDeliveryType(packageDTO.getDeliveryType());
        pack.setDeliveryAddress(packageDTO.getDeliveryAddress());
        pack.setWeight(packageDTO.getWeight());
        pack.setDeliveryFee(calculateDeliveryFee(packageDTO.getWeight(), packageDTO.getDeliveryType()));
        pack.setPrice(packageDTO.getPrice());
        pack.setStatus(packageDTO.getStatus());
        pack.setCompany(company);

        Package savedPackage = packageRepository.save(pack);

        revenueService.createRevenue(savedPackage);

        return savedPackage;
    }

    public Package updatePackage(Long id, PackageDTO packageDTO) {
        return packageRepository.findById(id).map(pack -> {

            // Fetch the associated company
            Company company = companyRepository.findById(packageDTO.getCompany_id())
                    .orElseThrow(() -> new RuntimeException("Company not found with id " + packageDTO.getCompany_id()));

            // Update entity fields
            pack.setSenderId(packageDTO.getSenderId());
            pack.setRecipientId(packageDTO.getRecipientId());
            pack.setCourierId(packageDTO.getCourierId());
            pack.setDeliveryType(packageDTO.getDeliveryType());
            pack.setDeliveryAddress(packageDTO.getDeliveryAddress());
            pack.setWeight(packageDTO.getWeight());
            pack.setPrice(packageDTO.getPrice());
            pack.setDeliveryFee(calculateDeliveryFee(packageDTO.getWeight(), packageDTO.getDeliveryType()));
            pack.setStatus(packageDTO.getStatus());
            pack.setCompany(company);

            Package updatedPackage = packageRepository.save(pack);

            revenueService.updateRevenue(updatedPackage);

            return updatedPackage;
        }).orElseThrow(() -> new RuntimeException("Package not found with id " + id));
    }
private BigDecimal calculateDeliveryFee(double weight, DeliveryType deliveryType) {
        BigDecimal deliveryFee = BigDecimal.ZERO;

        if (deliveryType == DeliveryType.OFFICE) {
            deliveryFee = BigDecimal.valueOf(5 + weight);
        } else if (deliveryType == DeliveryType.ADDRESS) {
            deliveryFee = BigDecimal.valueOf(10 + weight);
        }

        return deliveryFee;
    }
    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }
}
