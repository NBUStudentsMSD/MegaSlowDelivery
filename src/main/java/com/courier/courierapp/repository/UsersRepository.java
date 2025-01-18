package com.courier.courierapp.repository;

import com.courier.courierapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>
{
//add logic if needed
}
