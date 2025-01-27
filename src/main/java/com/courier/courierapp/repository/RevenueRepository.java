package com.courier.courierapp.repository;

import com.courier.courierapp.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    List<Revenue> findByPackageId(Long packageId);
}

