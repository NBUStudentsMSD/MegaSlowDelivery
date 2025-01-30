package com.courier.courierapp.repository;

import com.courier.courierapp.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findBySenderIdOrRecipientId(Long senderId, Long recipientId);
}
