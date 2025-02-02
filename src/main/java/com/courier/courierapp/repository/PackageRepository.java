package com.courier.courierapp.repository;

import com.courier.courierapp.model.Package;
import com.courier.courierapp.model.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findBySenderIdOrRecipientId(Long senderId, Long recipientId);

    List<Package> findByStatus(PackageStatus status);
    List<Package> findByCompanyId(Long companyId);
    List<Package> findByCompanyIdAndCreatedAtBetween(Long companyId, LocalDateTime startDate, LocalDateTime endDate);
    List<Package> findBySenderId(Long senderId);
    List<Package> findByRecipientId(Long recipientId);
}
