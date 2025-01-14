package com.courier.courierapp.repository;

import com.courier.courierapp.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
