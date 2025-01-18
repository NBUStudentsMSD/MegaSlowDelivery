package com.courier.courierapp.repository;

import com.courier.courierapp.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findByCompanyId(Long companyId);
}
