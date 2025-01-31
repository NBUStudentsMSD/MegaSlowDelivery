package com.courier.courierapp.repository;

import com.courier.courierapp.model.Company;
import com.courier.courierapp.model.DeliveryFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryFeeRepository  extends JpaRepository<DeliveryFee, Long> {
    Optional<DeliveryFee> findByCompanyId(Long companyId);
}