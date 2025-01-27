package com.courier.courierapp.service;

import com.courier.courierapp.model.Company;
import com.courier.courierapp.model.Revenue;
import com.courier.courierapp.model.Package;
import com.courier.courierapp.repository.CompanyRepository;
import com.courier.courierapp.repository.PackageRepository;
import com.courier.courierapp.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RevenueService {
    @Autowired
    private final RevenueRepository revenueRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private final PackageRepository packageRepository; // Предполага се, че имаш repository за пакети.

    public RevenueService(RevenueRepository revenueRepository, PackageRepository packageRepository) {
        this.revenueRepository = revenueRepository;
        this.packageRepository = packageRepository;
    }



    public List<Revenue> getAllRevenues() {
        return revenueRepository.findAll();
    }

    public Revenue getRevenueById(Long id) {
        return revenueRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Revenue not found with id: " + id));
    }



    public void createRevenue(Package pack) {
        Company company = pack.getCompany();

        Revenue revenue = new Revenue();


        revenue.setAmount(pack.getDeliveryFee());
        revenue.setRecordDate(LocalDate.now());
        revenue.setCompany(company);

        revenueRepository.save(revenue);

    }

//    public Revenue updateRevenue(Package pack) {
//        Revenue revenue = revenueRepository.findByPackageId(pack.getId()).get(0);
//
//
//            // Fetch the associated company
//            Company company = pack.getCompany();
//            // Update entity fields
//            BigDecimal calculatedAmount = pack.getDeliveryFee();
//            revenue.setAmount(calculatedAmount);
//            revenue.setRecordDate(LocalDate.now());
//            revenue.setCompany(company);
//
//
//            return revenueRepository.save(revenue);
//
//    }

    public void deleteRevenue(Long id) {
        revenueRepository.deleteById(id);
    }

    public BigDecimal calculateRevenueFromPackage(Package pack) {
        // Можеш да внедриш сложна логика тук за изчисляване на приходите
        return pack.getPrice(); // Пример: Цена на пакета като приход.
    }
}


