package com.courier.courierapp.service;

import com.courier.courierapp.model.Package;
import com.courier.courierapp.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public Optional<Package> getPackageById(Long id) {
        return packageRepository.findById(id);
    }

    public Package createPackage(Package pack) {
        return packageRepository.save(pack);
    }

    public Package updatePackage(Long id, Package packageDetails) {
        return packageRepository.findById(id).map(pack -> {
            pack.setSenderId(packageDetails.getSenderId());
            pack.setRecipientId(packageDetails.getRecipientId());
            pack.setCourierId(packageDetails.getCourierId());
            pack.setDeliveryType(packageDetails.getDeliveryType());
            pack.setDeliveryAddress(packageDetails.getDeliveryAddress());
            pack.setWeight(packageDetails.getWeight());
            pack.setPrice(packageDetails.getPrice());
            pack.setStatus(packageDetails.getStatus());
            return packageRepository.save(pack);
        }).orElseThrow(() -> new RuntimeException("Package not found with id " + id));
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }
}

