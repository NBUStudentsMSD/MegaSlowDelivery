package com.courier.courierapp.repository;

import com.courier.courierapp.model.Employee;
import com.courier.courierapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //add logic
}
